/*
$(document).ready(function(){
 $('#start_date').datepicker();
 $('#end_date').datepicker();
});
*/

function submitRideSearch() {
  let start = new Date(document.getElementById('start_date').value);
  let end = new Date(document.getElementById('end_date').value);
  let today = new Date();

  if(!isNaN(start.getDate()) && !isNaN(end.getDate()) && start > end) { // start after end
      alert('Start date cannot follow end date.');
  }
  else if((!isNaN(start.getDate()) && start < today)) { // date in past
      alert('Date cannot be in the past');
  }
  else {
    document.getElementById('results').style.visibility = "visible";
  }
}
