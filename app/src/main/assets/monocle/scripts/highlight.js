function clearSelection(node){
    $(node).find('mark').each(function(){
        $(node).replaceWith($(node).html());//将他们的属性去掉；
    });
}

function highlightkeyword(node,keyword){
    var html = $(node).html();
    var newHtml = html.replace(keyword, '<mark style="color:red">'+keyword+'</mark>');
    $(node).html(newHtml);
}