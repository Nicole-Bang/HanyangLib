package kr.co.itwill.community;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;

@Controller
@RequestMapping("/comm")
public class CommunityCont {

    public CommunityCont(){
        System.out.println("-----CommunityCont() 객체 생성됨");
    }//end

    @Autowired
    CommunityDAO commDao;


    @RequestMapping("/index")
    public ModelAndView index(){
        ModelAndView mav = new ModelAndView();
        mav.addObject("newComm", commDao.newComm());

        String loginID = "hanyihanyi";
        mav.addObject("listMylib", commDao.listMylib(loginID));    //커뮤니티 생성시 내서재 조회하기
        mav.setViewName("community/commindex");
        return mav;
    }//index() end


    @RequestMapping("/list")
    public ModelAndView list(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("community/list");
        mav.addObject("list", commDao.list());
        return mav;
    }//list() end

    @RequestMapping("/search")
    public ModelAndView search(@RequestParam(defaultValue = "") String keyword){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("community/list");
        mav.addObject("keyword", keyword);
        mav.addObject("list", commDao.search(keyword));
        return mav;
    }//search() end


    @RequestMapping("/read")
    public ModelAndView read(@RequestParam String c_code, @RequestParam String loginID){
        ModelAndView mav = new ModelAndView();
        mav.addObject("loginID", loginID);
        //mav.addObject("c_code", c_code);
        mav.addObject("read", commDao.read(c_code));
        mav.addObject("acList", commDao.acList(c_code));
        mav.addObject("commCheck", commDao.commCheck(c_code));
        CommSignDTO sign = new CommSignDTO();
        sign.setS_code(c_code);
        sign.setS_id(loginID);
        mav.addObject("sign", sign);
        mav.addObject("idCheck", commDao.idCheck(sign));
        mav.setViewName("community/read");
        return mav;
    }//read() end


    //커뮤니티 가입 신청 (설문지랑 연결해야 함)
    @RequestMapping("/signupForm")
    public String signupForm(){
        return "community/signupForm";
    }//signupForm() end

    //커뮤니티 생성하기
    @RequestMapping("/createForm/{b_code}")
    public ModelAndView createForm(@PathVariable String b_code){

        ModelAndView mav = new ModelAndView();
        mav.addObject("readBook", commDao.readBook(b_code));
        mav.addObject("createCode", commDao.createCode());
        mav.addObject("list", commDao.list());
//        List<BookReadDTO> test = commDao.list();
//        for(int i=0; i<test.size(); i++){
//            System.out.println(test);
//        }
        mav.setViewName("community/createForm");
        return mav;
    }//createForm() end


    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ModelAndView createForm(@ModelAttribute CommunityDTO dto, HttpServletRequest request) throws Exception{
        //파일명 리네임 구현 안 했음.
        ModelAndView mav = new ModelAndView();

        //저장 폴더의 실제 경로 가져오기
        ServletContext application = request.getSession().getServletContext();
        String path = application.getRealPath("/storage");

        //<input type="file" name="poster">
        MultipartFile poster = dto.getPoster();                         //파일 가져오기
        String filename = poster.getOriginalFilename();                 //파일 이름 가져오기
        if(poster == null || poster.isEmpty()){                         //파일 없을 경우 기본 이미지
            filename = "none.jpg";
        }//if end
        poster.transferTo(new File(path + "/" + filename));    // /storage 폴더에 파일 저장

        dto.setC_banner(filename);

        dto.setC_code(dto.getC_code());
        dto.setC_name(dto.getC_name());
        dto.setC_des(dto.getC_des());
        dto.setC_bcode(dto.getC_bcode());
        dto.setC_local(dto.getC_local());
        dto.setC_id("hanyihanyi");
        dto.setC_chat(dto.getC_chat());
        dto.setC_count(dto.getC_count());

        mav.addObject("community", commDao.insert(dto));
        mav.setViewName("redirect:/comm/index");    //메인으로 돌아가기
        return mav;
    }//createForm() end


    @RequestMapping("/delete/{c_code}")
    public String delete(@PathVariable String c_code, HttpServletRequest request){

        String filename = commDao.filename(c_code); //삭제할 파일이름 조회
        if(!filename.equals("none.jpg")){
            ServletContext application = request.getSession().getServletContext();
            String path = application.getRealPath("/storage");
            File file = new File(path + "/" + filename);
            if(file.exists()){
                file.delete();
            }//file() end
        }//if end

        commDao.delete(c_code);
        return "redirect:/comm/index";
    }//delete() end


    @RequestMapping("/update/{c_code}")
    public ModelAndView update(@PathVariable String c_code){
        ModelAndView mav = new ModelAndView();
        mav.addObject("read", commDao.read(c_code));
        mav.setViewName("community/update");
        return mav;
    }//update() end

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView update(@ModelAttribute CommunityDTO dto, HttpServletRequest request) throws Exception{

        ModelAndView mav = new ModelAndView();


        //기존 저장된 정보 가져오기
        BookReadDTO oldDTO = commDao.read(dto.getC_code());

        String filename = "";
        //파일을 수정할 것인지?
        MultipartFile poster = dto.getPoster();         //파일 가져오기
        if(poster.getSize() > 0){                       //새파일이 업로드 되었다면
             filename = poster.getOriginalFilename();   //새로운 파일명

            try {
                //저장 폴더의 실제 경로 가져오기
                ServletContext application = request.getSession().getServletContext();
                String path = application.getRealPath("/storage");
                poster.transferTo(new File(path + "/" + filename));   //신규로 전송된 파일 저장
                dto.setC_banner(filename);                                     //리네임된 파일명 dto에 담기

                String oldFilename = oldDTO.getC_banner();                     //old파일 /storage에서 삭제하기
                File oldFile = new File(path + "/" + oldFilename);
                if(oldFile.exists()){
                    oldFile.delete();
                }//file() end

            }catch (Exception e){
                e.printStackTrace();
            }//try end

        }else {                                         //파일 수정 안 할 경우
            filename = oldDTO.getC_banner();            //oldDTO에서 파일이름 가져오기
            dto.setC_banner(oldDTO.getC_banner());      //기존파일이름 dto에 담기
        }//if end

        dto.setC_code(dto.getC_code());
        dto.setC_name(dto.getC_name());
        dto.setC_des(dto.getC_des());
        dto.setC_local(dto.getC_local());
        dto.setC_chat(dto.getC_chat());
        dto.setC_count(dto.getC_count());

        mav.addObject("update", commDao.update(dto));
        mav.setViewName("redirect:/comm/index"); //수정 필요 : 관리자 페이지로 이동
        return mav;
    }//update() end

}//class end
