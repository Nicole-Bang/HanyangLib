<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../header.jsp"%>
<!-- 본문작성 시작 commindex.jsp -->
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!-- 타이틀 사진과 상단 검색창 -->
<div class="hero-slant overlay" data-stellar-background-ratio="0.5" style="background-image: url('../images/galaxy.jpeg'); height: 90vh;">
    <div class="container">
        <div class="row align-items-center justify-content-between" style="display:flex; flex-direction: row-reverse;">
            <div class="col-lg-12 intro">
                <h1 class="text-white text-right font-weight-bold mb-4" data-aos="fade-up" data-aos-delay="0" style="text-shadow: 3px 3px 3px black;">책 한 권 속에 펼쳐져있는<br>수 만가지 이야기를<br>검색해보세요</h1>
                <form action="search" method="post" class="sign-up-form d-flex" data-aos="fade-up" data-aos-delay="200">
                    <input type="text" name="keyword" class="form-control" value="${keyword}" placeholder="커뮤니티 이름 또는 책 이름을 검색해보세요">
                    <button type="submit" class="btn-dark" style="width:40px; padding: 0px;"><i class="fa-solid fa-magnifying-glass text-center"></i></button>
                </form>
            </div>
        </div>
    </div>
</div>

<div class="container pb-md-5"></div>



<!---------------------------------------------------------------------------->
<!-- 인기 커뮤니티 -->
<div class="container pb-md-5 border-bottom">
    <div class="row">
        <div class="col-lg-12">
            <a href="list"><!-- 인기순 list로 가기-->
                <h1 class="listtext-black text-left font-weight-bold mb-4" data-aos="fade-up" data-aos-delay="0">종이에 쓰인<br>글자만으로<br>이런 모임이</h1>
            </a>
        </div>
        <div class="col-lg-12">
            <div class="row" data-aos="fade-up" data-aos-delay="0">
                <div class="col-lg-4">
                    <!-- 사진 클릭하면 해당 커뮤니티 상세보기 이동 -->
                    <a href="read" class="gal-item"><img src="../images/img_h_2-min.jpg" class="img-fluid"></a>
                    <br>
                    <h4 style="text-align: center; font-weight: bold">커뮤니티명</h4>
                    <p style="text-align: center; font-weight: bold">책 이름</p>
                </div>
                <div class="col-lg-4">
                    <a href="" class="gal-item"><img src="../images/img_h_2-min.jpg" class="img-fluid"></a>
                    <br>
                    <h4 style="text-align: center; font-weight: bold">커뮤니티명</h4>
                    <p style="text-align: center; font-weight: bold">책 이름</p>
                </div>
                <div class="col-lg-4">
                    <a href="" class="gal-item"><img src="../images/img_h_2-min.jpg" class="img-fluid"></a>
                    <br>
                    <h4 style="text-align: center; font-weight: bold">커뮤니티명</h4>
                    <p style="text-align: center; font-weight: bold">책 이름</p>
                </div>
            </div>
        </div>
    </div>
</div>


<!-- 신규 커뮤니티 -->
<div class="container pb-md-5"></div>
<div class="container pb-md-5 border-bottom">
    <div class="row">
        <div class="col-lg-12">
            <a href="list"><!-- 최신순 list로 가기-->
                <h1 class="text-black text-right font-weight-bold mb-4" data-aos="fade-up" data-aos-delay="0">새로운 모임은<br>언제든 환영이야</h1>
            </a>
        </div>
        <div class="col-lg-12">
            <div class="row" data-aos="fade-up" data-aos-delay="0">

                <c:forEach items="${newComm}" var="dto">
                <div class="col-lg-4">
                    <a href="read?c_code=${dto.c_code}&loginID=hanyihanyi" class="gal-item">
                        <c:choose>
                            <c:when test="${dto.b_bookcover == null}">
                                <img src="/storage/none.jpg" class="img-fluid"><!-- 실제로는 c_banner기본값이 none.jpg이므로 신경쓰지 않아도 된다-->
                            </c:when>
                            <c:otherwise>
                                <img src="/storage/none.jpg" class="img-fluid">
                            </c:otherwise>
                        </c:choose>


                    </a>
                    <br>
                    <h4 style="text-align: center; font-weight: bold">${dto.c_name}</h4>
                    <p style="text-align: center; font-weight: bold">${dto.b_name}</p>
                </div>
                </c:forEach>
            </div>
        </div>
    </div>
</div>


<!-- 마감일 임박 커뮤니티 -->
<div class="container pb-md-5"></div>
<div class="container pb-md-5 border-bottom">
    <div class="row" data-aos="fade-up" data-aos-delay="0">
        <div class="col-lg-12">
            <a href="list"><!-- 마감순 list로 가기-->
                <h1 class="text-black text-left font-weight-bold mb-4" data-aos="fade-up" data-aos-delay="0">열두시가 되면은<br>문을 닫는다</h1>
            </a>
        </div>
        <div class="col-lg-12">
            <div class="row">
                <div class="col-lg-4">
                    <a href="" class="gal-item"><img src="../images/img_h_2-min.jpg" class="img-fluid"></a>
                    <br>
                    <h4 style="text-align: center; font-weight: bold">커뮤니티명</h4>
                    <p style="text-align: center; font-weight: bold">책 이름</p>
                </div>
                <div class="col-lg-4">
                    <a href="" class="gal-item"><img src="../images/img_h_2-min.jpg" class="img-fluid"></a>
                    <br>
                    <h4 style="text-align: center; font-weight: bold">커뮤니티명</h4>
                    <p style="text-align: center; font-weight: bold">책 이름</p>
                </div>
                <div class="col-lg-4">
                    <a href="" class="gal-item"><img src="../images/img_h_2-min.jpg" class="img-fluid"></a>
                    <br>
                    <h4 style="text-align: center; font-weight: bold">커뮤니티명</h4>
                    <p style="text-align: center; font-weight: bold">책 이름</p>
                </div>
            </div>
        </div>
    </div>
</div>


<!-- 본문작성 끝 -->
<%@ include file="../footer.jsp"%>