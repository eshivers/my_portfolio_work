

function viewStudentStats(id, toggle)
{
	if(toggle == 0)
	{
		document.getElementById(id).style.visibility = "hidden";
		document.getElementById(id).style.height = "0px";
	}
	else
	{
		document.getElementById(id).style.visibility = "visible";
		document.getElementById(id).style.height = "auto";
	}
}



/**
* This method will display the form for the selected option and
* 	hide all other forms
* @param {element}: dropdown the dropdown menu
* @param {string}: prefix for form groups of each value
*/
function switchPlayerForm(dropdown, prefix) {
	const active_suffix = dropdown.options[dropdown.selectedIndex].value;
	for (let option_idx = 1; option_idx < dropdown.options.length; option_idx++) {
		let = curr_suffix = dropdown.options[option_idx].value;
		if (curr_suffix == active_suffix){
			viewStudentStats(`${prefix}-${curr_suffix}`, 1);
		}
		else {
			viewStudentStats(`${prefix}-${curr_suffix}`, 0);
		}
	}
}


/**
* This method will reset the playerinfo form
*/
$('#formtype_select option').each(function () {
    if (this.defaultSelected) {
        this.selected = true;
        return false;
    }
});
