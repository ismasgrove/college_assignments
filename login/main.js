function validateReg() {
    var x = document.forms["reg_form"]["email"].value;
    var y = document.forms["reg_form"]["username"].value;
    var z = document.forms["reg_form"]["password"].value;
    var flag = 0;
    var warning = "The following errors have occured:\n";
    if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(x)) {
    } else {
        flag = 1;
        warning += "-E-mail format is incorrect.\n";
    }
    if (y == "") {
        flag = 1;
        warning += ("-Username field is empty.\n");
    }
    if (z == "") {
        flag = 1;
        warning += ("-Password field is empty.\n");
    }

    if (flag == 0) {
        return true;
    }

    else {
        alert(warning);
        return false;
    }
}

function validateLog(){
        var y = document.forms["login_form"]["username"].value;
        var z = document.forms["login_form"]["password"].value;
    var flag = 0;
    var warning = "The following errors have occured:\n";
    if (y == "") {
        flag = 1;
        warning += ("-Username field is empty.\n");
    }
    if (z == "") {
        flag = 1;
        warning += ("-Password field is empty.\n");
    }

    if (flag == 0) {
        return true;
    }

    else {
        alert(warning);
        return false;
    }
}