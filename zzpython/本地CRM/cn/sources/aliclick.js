function aliclick(u, param) {
    d = new Date();
    if(document.images) {
        (new Image()).src="http://page.china.alibaba.com/stat/stat.html" + param + "&time=" + d.getTime();
    }
    return true;
}