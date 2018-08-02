

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

        },
        complete:function(response){
            if(response.status=='204')
                $(".image").html("Error");
            else if(response.status=='200')
                $(".image").html("Success");
            $(".container-main").fadeOut().hide();
            $(".xyz img").css("transform","scale(1)");
            $(".xyz img").addClass("animate");

        }
    });
});