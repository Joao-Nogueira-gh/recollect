const appName = "reCollect";


$(document).ready(function(){
    //document.getElementsByTagName("html")[0].setAttribute("xmlns:sec","http://www.thymeleaf.org/extras/spring-security");

    const tagline = document.getElementsByTagName("title")[0];
    tagline.innerText = appName;
    tagline.innerHTML = appName;

    //buildNavbar();
    buildFooter();
    buildFooterBottom();

});



function buildNavbar() {
    const navbarContent = `<section>
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <nav class="navbar navbar-expand-lg navbar-light navigation">
                    <a class="navbar-brand" href="/">
                        <img src="images/logo.png" alt="">
                    </a>
                    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
                            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                        <span class="navbar-toggler-icon"></span>
                    </button>
                    <div class="collapse navbar-collapse" id="navbarSupportedContent">
                        <ul class="navbar-nav ml-auto main-nav ">
                            <li class="nav-item">
                                <a class="nav-link" href="/">Home</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="/about">About us</a>
                            </li>

                        </ul>
                        <ul class="navbar-nav ml-auto mt-10">
                            <li class="nav-item" sec:authorize="!isAuthenticated()">
								<a class="nav-link login-button" href="/login">Login</a>
							</li>
							<li class="nav-item" sec:authorize="isAuthenticated()">
								<a class="nav-link login-button" href="/profile">Profile</a>
							</li>
						
                            <li class="nav-item">
                                <a class="nav-link text-white add-button" href="/announce"><i class="fa fa-plus-circle"></i> Announce</a>
                            </li>
                        </ul>
                    </div>
                </nav>
            </div>
        </div>
    </div>
</section>`;

    $("#navbar").html(navbarContent);
}

function buildFooter() {

    /*
    * <div>
        <div id="teste_div"></div>
        <div id="API_result"></div>
        <!-- <h4 th:text="${'Nome: ' + item.nome + '\nPreço: ' + item.preco + '\nQuantidade: ' + item.quantidade}"></h4> -->
    </div>
    *
    * */
    const footerContent = `<footer class="footer section section-sm">
 
    <!-- Container Start -->
    <div class="container">
        <div class="row">
            <div class="col-lg-3 col-md-7 offset-md-1 offset-lg-0">
                <!-- About -->
                <div class="block about">
                    <!-- footer logo -->
                    <img src="images/logo-footer.png" alt="reCollect">
                    <!-- description -->
                    <!-- <p class="alt-color">ReCollect is a platform that allows users to sell their old collections, from simple things such as figurines sold some years ago in stores to rarer items like limited editions of a certain product (book, game, etc.). Here, the buyers can find items they need in order to complete their collections or even start brand new ones!</p> -->
                </div>
            </div>
            <!-- Link list -->
            <div class="col-lg-2 offset-lg-1 col-md-3">
                <div class="block">
                    <h4>Useful Links</h4>
                    <ul>
                        <li><a href="/about">About us</a></li>
                        <li><a href="#">Conctact us</a></li>
                        <li><a href="#">Terms & Conditions</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
    <!-- Container End -->
</footer>`;

    $("#global-footer").html(footerContent);

}

function buildFooterBottom() {

    const footerContent = `<footer class="footer-bottom">
    <!-- Container Start -->
    <div class="container">
        <div class="row">
            <div class="col-sm-6 col-12">
                <!-- Copyright -->
                <div class="copyright">
                    <p>Copyright © 2020. All Rights Reserved, theme by <a class="text-primary" href="https://themefisher.com" target="_blank">themefisher.com</a></p>
                </div>
            </div>
            <div class="col-sm-6 col-12">
                <!-- Social Icons -->
                <ul class="social-media-icons text-right">
                    <li><a class="fa fa-facebook" href="https://www.facebook.com/themefisher" target="_blank"></a></li>
                    <li><a class="fa fa-twitter" href="https://www.twitter.com/themefisher" target="_blank"></a></li>
                    <li><a class="fa fa-pinterest-p" href="https://www.pinterest.com/themefisher" target="_blank"></a></li>
                    <li><a class="fa fa-vimeo" href=""></a></li>
                </ul>
            </div>
        </div>
    </div>
    <!-- Container End -->
    <!-- To Top -->
    <div class="top-to">
        <a id="top" class="" href="#"><i class="fa fa-angle-up"></i></a>
    </div>
</footer>`;

    $("#footer-bottom").html(footerContent);

}