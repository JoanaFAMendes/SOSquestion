$(document).ready(function () {


            // Api
            var apiPath = "http://localhost:8080/SOSquestion/api";
            var apiKey;


            //subjects
            var subjects = [{}];
            var subjectSearch;
            var subjectData;
            var subjectID;
            var subjectSearchChange;
            var subjectDelete;





            // Get all subjects
                apiKey = sessionStorage.getItem("apiKey");
                var url = apiPath + "/subjects?apiKey=" + apiKey;
                
                $.ajax({
                    url: url,
                    type: "GET",
                    
                    success: function (data) {
                        //console.log(data);
                        //return an array of objects type question
                        subjects = data;
    
                        if ($("#subjectsList") != undefined) {
                            $("#subjectsList").empty();
                            $("#subjectListQ").empty();
                            let content;
                            let contentquestions;
                            for (let i = 0; i < subjects.length; i++) {
                                const element = subjects[i];
                                if(i%3==0){
                                	content += "<br>";
                                }
                                content += "<div style='border-style: double;' id='viewS"+i+"' class='"+ element.description +"'><h3>" + element.description + "</h3>" + "</div>"
                                contentquestions+='<option value=' + element.description + '>' + element.description + '</option>';
                                
                                //console.log(contentApps , element.appID);
                            }
                            $("#subjectsList").append(content);
                            $("#subjectListQ").append(contentquestions);
                           
                        }
                        
                        for(var t=0; t<subjects.length; t++){
                        	//Get Specific subject
                            $("#viewS"+t).click(function(){
                                apiKey = sessionStorage.getItem("apiKey");
                                
                                subjectID = $(this).attr('class');
                                console.log(subjectID);
                                
                                sessionStorage.setItem("subjectID", subjectID);
                                window.open("./questions.html","_self");
                            })
                        }
                    },
                    error: function (request, status, error) {
                        console.log(request.responseText);
                    }
                
                })

            

             


            //Create subject
            $("#btnCreateSub").click(function () {
                apiKey = sessionStorage.getItem("apiKey");
                var description = $("#txtSubDescription").val();

                var form_data = {
                		description: description,
                		a:apiKey
                }
                var url = apiPath + "/subjects?apiKey=" + apiKey;
                
                $.ajax({
                    url: url,
                    type: "POST",
                    data: form_data,
                    success: function (data) {

                        console.log(data);

                        console.log("Created")

                    },
                    error: function (request, status, error) {
                        console.log(request.responseText);
                    }
                })
            });

            /*
            // Change question
            $("#editQuestion").click(function () {
                apiKey = sessionStorage.getItem("apiKey");

                questionSearchChange = $("#selectApp").val();
                
                author = $("#txtAppNameChange").val();
                subject = $("#txtAppTypeChange").val();
                description = $("#txtAppDescriptionChange").val();

                console.log(appSearchChange,author,subject,description)

                var form_data = {
                    author: author,
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