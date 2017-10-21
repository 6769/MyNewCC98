//document.write("<p>UserAgent: ");
//document.write(navigator.userAgent + "</p>");
var EMPTY='';
var elemId = "userid";
var APIid = "https://api.cc98.org/User/";
var APIname = "https://api.cc98.org/User/Name/";


var fixd=['portraitUrl','photoUrl'];

function convertImg(addr) {
    var h='<img width="180" src="addr"/>';

    return h.replace('addr',addr);
}

function transfrom(jsondat) {
    var res=[];
    for(var i in jsondat){
        item=jsondat[i];
        if(item===null ||item===EMPTY)continue;
        res.push({id:i,value:item});
    }

    return res;
}

function fetch_user_info(uid) {
    var fullUrl = APIid + uid;
    console.log(fullUrl);
    $.getJSON(fullUrl, function (data) {

        var templ=Tempo.prepare(elemId);
        templ.render(transfrom(data));
        for(var i in fixd){
            var node=$('td#'+fixd[i])[0];
            if(node===undefined)continue;
            node.innerHTML=convertImg(node.innerText);
        }
        $('table').show();

    });

}