function checkPassword(form) {
    email_entered = form.email_entered.value;
    email_db  = form.email_db.value;
    password_entered = form.password_entered.value;
    password_db = form.password_db.value;
    if (password_entered =='')
        alert ("Please enter Password");
          
    else if (password_db =='' && email_db == '')
        alert ("Account not found, Please register");
           
    else if (password_entered != password_db) {
        alert ("Password did not match: Please try again...")
        return false;
    }
    else{
        alert("Welcome to Alpine")
        return true;
    }
}
