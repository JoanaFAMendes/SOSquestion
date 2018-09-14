$(document).ready(function () {


            // Api
            var apiPath = "http://localhost:8080/SOSquestion/api";
            var apiKey;


            //myquestions
            var questions = [{}];
            var questionSearch;
            var questionData;
            var questionID;
            var userID;
            var questionP;
            var subjectID;
            var description;
            var questionSearchChange;
            var questionDelete;





            // Get all questions
                apiKey = sessionStorage.getItem("apiKey");
                userID = sessionStorage.getItem("username");
                var url = apiPath + "/questions/user/" + userID + "?apiKey=" + apiKey;
                
                $.ajax({
                    url: url,
                    type: "GET",
                    
                    success: function (data) {
                        //console.log(data);
                        //return an array of objects type question
                        questions = data;
                        
                        if ($("#myQuestions") != undefined) {
                            $("#myQuestions").empty();
                            let content;
                            let contentquestions;
                            for (let i = 0; i < questions.length; i++) {
                                const element = questions[i];
                                content += "<div >" + "<h3>Question: " + element.questionP + "</h3>" + "<br><p>Author: " + element.author + "</p><br>" + "<p>Subject: " + element.subject + "</p><br>" + "<h3>" + element.description + "</h3><br>" +
                                		"<a id='viewQ'  class='btn btn-simple btn-success btn-lg'  value='"+element.questionID+"' >View</a><br><br><a id='editQ'  class='btn btn-simple btn-success btn-lg'  value='"+element.questionID+"' >Edit</a><br><br><a id='deleteQ'  class='btn btn-simple btn-success btn-lg'  value='"+element.questionID+"' > Delete </a></div>"
                                //console.log(contentApps , element.appID);
                            }
                            $("#myQuestions").append(content);
                        }
                        for(var t=0; t<questions.length; t++){
                        	//Get Specific Question
                            $("#viewQ" + t).click(function(){
                                apiKey = sessionStorage.getItem("apiKey");
                                
                                questionID = $(this).attr('value');
                                
                                sessionStorage.setItem("questionID", questionID);
                                window.open("./q_a.html","_self");
                            });
                            
                            $("#editQ" + t).click(function(){
                                apiKey = sessionStorage.getItem("apiKey");
                                
                                questionID = $(this).attr('value');
                                
                                sessionStorage.setItem("questionID", questionID);
                                window.open("./createQuestion.html","_self");
                            });
                            
                            $("#deleteQ" + t).click(function(){
                            	 apiKey = sessionStorage.getItem("apiKey");
                                 
                                 questionDelete = $(this).attr('value');
                                 var url = apiPath + "/questions/" + questionDelete + "?apiKey=" + apiKey;
                                 $.ajax({
                                         url: url,
                                         type: "DELETE",
                                         success: function (data,xhr, textStatus) {
                                             console.log(data);
                                         }

                                 });
                            });
                            
                        }
                    },
                    error: function (request, status, error) {
                        console.log(request.responseText);
                    }
                })

            

             //Get Specific Question
                $("#viewQ").click(function(){
                    apiKey = sessionStorage.getItem("apiKey");
                    
                    questionID = this.val();
                    
                    sessionStorage.setItem("questionID", questionID);
                    window.open("./createQuestion.html","_self");
                });

             // Change question
            $("#btnEditQuestion").click(function () {
                apiKey = sessionStorage.getItem("apiKey");

                questionSearchChange = sessionStorage.getItem("questionID");
                
                questionP = $("#txtQuestionName").val();
                description = $("#txtQuestionDescription").val();
                subjectID = $( "#subjectListQ" ).val();

                console.log(appSearchChange,author,subject,description)

                var form_data = {
                	questionP:questionP,
                    subject: subject,
                    description: description,
                    apiKey: apiKey
                }
                var url = apiPath + "/questions/" + questionSearchChange + "/?apiKey=" + apiKey
                $.ajax({
                    url: url,
                    type: "POST",
                    data: form_data,
                    success: function (data) {
                        console.log("changed")
                    }
                })
            });
            
            //edit question
            $("#editQ").click(function(){
            	author = sessionStorage.getItem("username");
                apiKey = sessionStorage.getItem("apiKey");
                
                questionID = this.val();
                
                sessionStorage.setItem("questionID", questionID);
                window.open("./createQuestionn.html","_self");
            });

            //Delete  
            $("#deleteQ").click(function () {
                    apiKey = sessionStorage.getItem("apiKey");
                    
                    questionDelete = $(this).val()
                    var url = apiPath + "/questions/" + questionDelete + "?apiKey=" + apiKey;
                    $.ajax({
                            url: url,
                            type: "DELETE",
                            success: function (data,xhr, textStatus) {
                                console.log(data);
                            }

                    });
                });



            });