/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/ClientSide/javascript.js to edit this template
 */

function deleteSetting(id){
    var result = confirm("Do you want to delete?");
    if(result == true){
        window.location.href = "setting/delete/" + id;
    }
}


$("#search-category").keypress(function (e) {
    if (e.which == 13) {
        e.preventDefault();
    }
})

const showFilterbar = (element) => {
        $("#button-open-filter").removeClass("md:hidden");
    if ($("#filter-drag-layout").hasClass("hidden")) {
        $("#button-close-filter").removeClass("hidden");
        $("#filter-drag-layout").removeClass("hidden");
        $("#filter-drag-content").removeClass("hidden");
        $("#filter-drag-layout").addClass(`fixed top-[20px] right-[10px]`);
        $("#filter-drag-content").css({'top': -10, 'left' : 0});
    } else {
        $("#button-close-filter").addClass("hidden");
        $("#filter-drag-layout").addClass("hidden");
        $("#filter-drag-content").addClass("hidden");
    }
}

const closeFilter = () => {
    $("#button-open-filter").removeClass("md:hidden");
    $("#button-open-filter").removeClass("hidden");
    $("#filter-drag-content").addClass("hidden");
    $("#button-close-layout").addClass("hidden");
}