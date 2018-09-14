$(document).ready(function () {


            // Api
            var apiPath = "http://localhost:8080/SOSquestion/api";
            var apiKey;


            //questions
            var questions = [{}];
            var questionSearch;
            var questionData;
            var questionID;
            var author;
            var questionP;
            var subjectID;
            var description;
            var questionSearchChange;
            var questionDelete;





            // Get all questions by subject
                apiKey = sessionStorage.getItem("apiKey");
                subjectID = sessionStorage.getItem("subjectID");
                console.log(subjectID);
                var url = apiPath + "/questions/subjects/" + subjectID + "?apiKey=" + apiKey;
                
                $.ajax({
                    url: url,
                    type: "GET",
                    
                    success: function (data) {
                        //console.log(data);
                        //return an array of objects type question
                        questions = data;
            
                        if ($("#Questions") != undefined) {
                            $("#Questions").empty();
                            let content;
                            let contentquestions;
                            for (let i = 0; i < questions.length; i++) {
                                const element = questions[i];
                                content += "<div >" + "<h3>Question: " + element.questionP + "</h3>" + "<br><p>Author: " + element.author + "</p><br>" + "<p>Subject: " + element.subject + "</p><br>" + "<h3>" + element.description + "</h3><br>" +
                        		"<a id='viewQ"+ i +"'  class='btn btn-simple btn-success btn-lg'  value='"+element.questionID+"' >View</a></div>"
                                //console.log(contentApps , element.appID);
                            }
                            $("#Questions").append(content);
   
                          
                        }
                        for(var t=0; t<questions.length; t++){
                        	//Get Specific Question
                            $("#viewQ" + t).click(function(){
                                apiKey = sessionStorage.getItem("apiKey");
                                
                                questionID = $(this).attr('value');
                                
                                sessionStorage.setItem("questionID", questionID);
                                window.open("./q_a.html","_self");
                            });
                        }
                    }
                })
            

             


            //Create question
            $("#btnCreateQuestion").click(function () {
                apiKey = sessionStorage.getItem("apiKey");
                author = sessionStorage.getItem("username");
                questionP = $("#txtQuestionName").val();
                description = $("#txtQuestionDescription").val();
               subjectID = $( "#subjectListQ" ).val();

                var form_data = {
                   
                    author: author,
                    subjectID: subjectID,
                    questionP:questionP,
                    description: description,
                    apiKey: apiKey
                }
                var url = apiPath + "/questions?apiKey=" + apiKey;
                $.ajax({
                    url: url,
                    type: "POST",
                    data: form_data,
                    success: function (data) {

                        console.log(data);

                        console.log("Created")

                    }
                })
            });

             // Change question
            $("#editQuestion").click(function () {
                apiKey = sessionStorage.getItem("apiKey");

                questionSearchChange = sessionStorage.getItem("questionID");
                
                author = sessionStorage.getItem("username");
                subjectID = $("#txtAppTypeChange").val();
                description = $("#txtAppDescriptionChange").val();

                console.log(appSearchChange,author,subject,description)

                var form_data = {
                    author: author,
                    subjectID: subjectID,
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
            
            /*
            //edit question
            $("#edit").click(function(){
            	username = sessionStorage.getItem("username");
                apiKey = sessionStorage.getItem("apiKey");
                
                questionID = this.val();
                
                sessionStorage.setItem("questionID", questionID);
                //window.open("./bookmark.html","_self");
            });

            //Delete  
            $("#delete").click(function () {
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

*/

            });