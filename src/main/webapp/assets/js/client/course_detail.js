/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/ClientSide/javascript.js to edit this template
 */


const showLesson = (element,id) => {
    if($(`#${id}`).hasClass("hidden")){
        $(`#${id}`).removeClass("hidden");
    }else{
        $(`#${id}`).addClass("hidden");
    }
}