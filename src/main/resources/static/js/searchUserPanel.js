const cookie = document.cookie

if(cookie.startsWith("username=")) {
    console.log(123)
    document.getElementsByClassName("usr-panel")[0].style.display = "none";
    document.getElementsByClassName("usr-msg")[0].style.display="block";
    localStorage.setItem("username", cookie.split(";")[0].split("=")[1])
    document.getElementById("username").innerHTML = localStorage.getItem("username")
}
else if(localStorage.getItem("username")) {
    document.getElementsByClassName("usr-panel")[0].style.display = "none";
    document.getElementsByClassName("usr-msg")[0].style.display="block";
    document.getElementById("username").innerHTML = localStorage.getItem("username")
}