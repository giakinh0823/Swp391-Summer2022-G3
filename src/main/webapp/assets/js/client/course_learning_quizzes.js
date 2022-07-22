/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/ClientSide/javascript.js to edit this template
 */

// Set the date we're counting down to
var countDownDate = new Date(end_time).getTime();
// Update the count down every 1 second
var x = setInterval(function () {
    // Get today's date and time
    var now = new Date().getTime();
    // Find the distance between now and the count down date
    var distance = countDownDate - now;
    // Time calculations for days, hours, minutes and seconds
    var days = Math.floor(distance / (1000 * 60 * 60 * 24));
    var hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
    var minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
    var seconds = Math.floor((distance % (1000 * 60)) / 1000);

    document.getElementById("quizzes-count-down").innerHTML = (days * 24 + hours) + "h " + minutes + "m " + seconds + "s ";
    // If the count down is finished, write some text
    if (distance < 0) {
        clearInterval(x);
        document.getElementById("quizzes-count-down").innerHTML = "EXPIRED";
        $("#button-submit").submit();
    }
}, 1000);


const addAnswer = (question, answer) => {
    const mySet = new Set();
    $(`input[name='answer-${question}']:checked`).each(function () {
        mySet.add($(this).val());
    });
    if ($(`input[name='answer-${question}']:checked`).length > 0) {
        $(`#question-view-${question}`).addClass("bg-green-400");
    } else {
        $(`#question-view-${question}`).removeClass("bg-green-400");
    }

    answers.set(question, mySet);
    console.log(answers);
    
    const list_question = [];
    const list_answer = [];
    for (const [key, value] of answers) {
        console.log(key, value);
        value.forEach(function (answer) {
            list_question.push(key);
            const number = Number(answer);
            list_answer.push(number);
        });
    }
    console.log(list_question);
    console.log(list_answer);
    $.ajax({
        method: "POST",
        url: window.location.pathname + '/choose',
        data: {question: list_question, answer: list_answer},
        dataType: 'json',
        success: function (data, textStatus, jqXHR) {
            
        }
    });
};


$("#form").on('submit', function (event) {
    event.preventDefault();
    const list_question = [];
    const list_answer = [];
    for (const [key, value] of answers) {
        console.log(key, value);
        value.forEach(function (answer) {
            list_question.push(key);
            const number = Number(answer);
            list_answer.push(number);
        });
    }
    console.log(list_question);
    console.log(list_answer);
    $.ajax({
        method: "POST",
        url: window.location.pathname + '/submit',
        data: {question: list_question, answer: list_answer},
        dataType: 'json',
        success: function (data, textStatus, jqXHR) {
            window.location.pathname = contextPath + '/course/learning/'+subjectId+'/lesson/'+lessonId;
        },
        error: function (error) {
            document.getElementById("quizzes-count-down").innerHTML = error.responseJSON.message;
        }
    });
});

