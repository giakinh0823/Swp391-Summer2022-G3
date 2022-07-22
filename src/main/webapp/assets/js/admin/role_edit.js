var checkboxs = $("input[name='features']");
var checkboxs_checked = $("input[name='features']:checked");
 $("#checked-checkbox-all").prop("checked", checkboxs.length == checkboxs_checked.length);

const selectAllFeature = (element) => {
    $("input[name='features']").prop("checked", $(element).is(":checked"));
}

const checkboxFeatureChange = () => {
    var checkboxs = $("input[name='features']");
    var checkboxs_checked = $("input[name='features']:checked");
    $("#checked-checkbox-all").prop("checked", checkboxs.length == checkboxs_checked.length);
}