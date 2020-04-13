function post() {
    var questionId = $("#question_id").val();//根据id拿到questionid
    var content = $("#comment_content").val();//根据id拿到content文本
    $.ajax({//利用ajax来实现异步刷新
        type:"POST",
        url: "/comment",
        contentType:'application/json',
        data:JSON.stringify({    //json.stringify()把javascript对象转字符串
            "parentId":questionId,
            "content":content,
            "type": 1
        }),
        success:function(response){
            if(response.code ==200){
                $("#comment_section").hide();//框隐藏
            }else {
                if(response.code=2003){//没登陆
                    var isAccepted = confirm(response.message);//win自带框，显示错误信息,看用户是否要登陆
                    if(isAccepted){//登陆
                        window.open("https://github.com/login/oauth/authorize?client_id=75c571318c6db9127268&redirect_uri=http://localhost:8887/callback&scope=user&state=1");//win自带方法，打开地址
                        window.localStorage.setItem("closable",true);//保存数据 key,value
                    }
                }else {
                    alert(response.message);
                }
            }
        },
        dataType:"json"
    });
}