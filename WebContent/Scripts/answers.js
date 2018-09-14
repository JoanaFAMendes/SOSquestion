$(document).ready(function () {


    // Api
    var apiPath = "http://localhost:8080/SOSquestion/api";
    var username;
    var apiKey;
    var selectedQuestion;



    //answers
    var answers = [{}];
    var answerSearch;
    var answerData;
    var answerID;
    var author;
    var date;
    var description;
    var answerSearchChange;
    var answerDelete;


    apiKey = sessionStorage.getItem("apiKey");
    
    var content;
    //Get a specific question
    $("#Question").empty();
    selectedQuestion = sessionStorage.getItem("questionID");
    
    var url = apiPath + "/questions/" + selectedQuestion + "?apiKey=" + apiKey
    $.ajax({
        url: url,
        type: "GET",
        success: function (data) {
            console.log(data);
            //return object type question
            var element = data;
           
            content += "<div >" + "<h3>Question: " + element.questionP + "</h3>" + "<br><p>Author: " + element.author + "</p><br>" + "<p>Subject: " + element.subject + "</p><br>" + "<h3>" + element.description + "</h3><br>" +
    		"</div>"        
        $("#Question").html(content);
        }
    })


    // Get all answers from question
    $("#btnAnswer").click(function () {
        
        username = sessionStorage.getItem("username");
        apiKey = sessionStorage.getItem("apiKey");
        console.log(selectedQuestion);
        var url = apiPath + "/questions/" + selectedQuestion + "/answers?apiKey=" + apiKey;
        $.ajax({
            url: url,
            type: "GET",
            success: function (data) {
                console.log(data);
                //return an array of objects type answer
                answers = data;

                if ($("#answers") != undefined) {
                    $("#answers").empty();
                    let content;
                    let contentAch;
                    
                    for (let i = 0; i < answers.length; i++) {
                        const element = answers[i];
                        content += "<div >" +  "<p>Author: " + element.author + "</p><br>" + "<p>Date: " + element.date + "</p><br>" + "<h3>" + element.description + "</h3><br>" +
                		"<a id='editA"+i+"'  class='btn btn-simple btn-success btn-lg'  value='"+element.answerID+"'>Edit</a><br><br><a id='deleteA"+i+"'  class='btn btn-simple btn-success btn-lg'  value='"+element.answerID+"' > Delete </a></div>"
                    }
                    $("#answers").append(content);

                }
                for(var t=0; t<answers.length; t++){
                	//Edit a specific answer
                    $("#editA" + t).click(function(){
                        apiKey = sessionStorage.getItem("apiKey");
                        
                        answerID = $(this).attr('value');
                        
                        sessionStorage.setItem("answerID", answerID);
                        window.open("./createAnswers.html","_self");
                    });
                    
                  //delete a specific answer
                    $("#deleteA" + t).click(function(){
                    	username = sessionStorage.getItem("username");
                        apiKey = sessionStorage.getItem("apiKey");

                        answerDelete = $(this).attr('value');
                        var form_data = {
                            
                            apiKey: apiKey
                        }
                        var url = apiPath + "/questions/" + selectedQuestion + "/answers/" + answerDelete + "?apiKey=" + apiKey;
                        $.ajax({
                            url: url,
                            type: "DELETE",
                            data: form_data,
                            success: function (data) {
                                console.log(data);

                            }
                        })
                    });
                }
                
            }
        })
    });

    /*
    //Get answer
    $("#btnGetAnswer").click(function () {
        selectedQuestion = $("#selectQuestion").val();
        username = sessionStorage.getItem("username");
        apiKey = sessionStorage.getItem("apiKey");
        var done;
        var content;
        answerSearch = $("#txtAnswertIDSearch").val();
        var url = apiPath + "/questions/" + selectedQuestion + "/answers/" + answerSearch + "?apiKey=" + apiKey
        $.ajax({
            url: url,
            type: "GET",
            success: function (data) {
                console.log(data);
                //return object type answer
                answerData = data;
                content += "<tr><td>" + answerData.answerID + "</td>" + "<td>" + answerData.questionID + "</td>" + "<td>" + answerData.author + "</td>" + "<td>" + answerData.date + "</td>" + "<td>" + answerData.description + "</td>" + "</tr>";
                $("#Answer").html(content);
            } 
            
        })
    });*/

    //Create answer
    $("#btnCreateAnswer").click(function () {
        //selectedQuestion = $("#selectQuestion").val();
        username = sessionStorage.getItem("username");
        apiKey = sessionStorage.getItem("apiKey");
        
console.log(selectedQuestion);
        
        author = sessionStorage.getItem("username");
        var d = new Date();
        date = d.getFullYear() + "/" + (d.getMonth()+1) + "/" + d.getDate();
        description = $("#txtAnswerDescription").val();

        var form_data = {
        		question: selectedQuestion,
            author: author,
            date: date,
            description: description,
            apiKey: apiKey
        }

        console.log(form_data,selectedQuestion)

        var url = apiPath + "/questions/" + selectedQuestion + "/answers?apiKey=" + apiKey;

        $.ajax({
            url: url,
            type: "POST",
            data: form_data,
            success: function (data) {
                
                console.log(data);

            }
        })
    });

    // Change answer 
    $("#btnEditAnswer").click(function () {
        
       // username = sessionStorage.getItem("username");
        apiKey = sessionStorage.getItem("apiKey");

        answerSearchChange = sessionStorage.getItem("answerID");
        console.log(selectedQuestion,answerSearchChange)
        author = sessionStorage.getItem("username");
        var d = new Date();
        date = d.getFullYear() + "/" + (d.getMonth()+1) + "/" + d.getDate();
        description = $("#txtAnswerDescription").val();

        var form_data = {
            author: author,
            date: date,
            description: description,
            apiKey: apiKey
        }
        var url = apiPath + "/questions/" + selectedQuestion + "/answers/" + answerSearchChange + "?apiKey=" + apiKey;
        $.ajax({
            url: url,
            type: "PUT",
            data: form_data,
            success: function (data) {
                console.log("changed")
            }
        })
    });



});