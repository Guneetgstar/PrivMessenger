

$(function(){

    $('#myForm').ajaxForm({

        beforeSend:function(){
            $(".progress").show();
        },
        uploadProgress:function(event,position,total,percentComplete){
            $(".progress-bar").width(percentComplete+'%');
            $(".sr-only").html(percentComplete+'%');
        },
        success:function(){
            $(".progress").hide();
        },
        complete:function(response){
            if(response.statusCode=='204')
                $(".image").html("Error");
            else if(response.statusCode=='200')
                $(".image").html("Success");
            $("#over").css("margin-top","0px");

        }
    });


    $(".progress").hide();
});