$(document).ready(function() {
    //입력폼 관련 이벤트 처리(회원가입)
    const inputAddress = document.getElementById('address');
    const inputPW = document.getElementById('password');
    const inputPW_C = document.getElementById('pw_confirm');
    const inputEmail = document.getElementById('email');
    const inputName = document.getElementById('name');
    const address_placeholder = document.getElementById('address_placeholder');
    const pwPlaceholder = document.getElementById('pw_placeholder');
    const namePlaceholder = document.getElementById('name_placeholder');
    const pwCPlaceholder = document.getElementById('pw_C_placeholder');
    const emailPlaceholder = document.getElementById('email_placeholder');
    const tooltip_name = document.getElementById('tooltip_name');
    const tooltip_Address = document.getElementById('tooltip_Address');
    const tooltip_pw = document.getElementById('tooltip_pw');
    const tooltip_pwC = document.getElementById('tooltip_pwC');
    const tooltip_email = document.getElementById('tooltip_email');

    const inputZipcode = document.getElementById('zipCode');
    const zipcodePlaceholder = document.getElementById('zipcode_placeholder')
    const tooltip_zipcode = document.getElementById('tooltip_zipcode');

    const inputDetailAddress = document.getElementById('detail_Address');
    const detailPlaceholder = document.getElementById('detail_placeholder')
    const tooltip_detail = document.getElementById('tooltip_detail');

    console.log("동작준비 완료");

    // console.log(inputAddress);


    inputName.addEventListener('focus', () => {

        $(namePlaceholder).addClass('focused');
        $(tooltip_name).removeClass('hidden');
    });
    inputName.addEventListener('blur', () => {
        if(inputName.value==null || inputName.value=='') {
            $(namePlaceholder).removeClass('focused');
        } else {
            $(namePlaceholder).addClass('focused');
        }
        $(tooltip_name).addClass('hidden');
    });

    inputZipcode.addEventListener('focus', () => {
        $(zipcodePlaceholder).addClass('focused');
        $(tooltip_zipcode).removeClass('hidden');
    });
    inputZipcode.addEventListener('blur', () => {
        if(inputZipcode.value==null || inputZipcode.value=='') {
            $(zipcodePlaceholder).removeClass('focused');
        } else {
            $(zipcodePlaceholder).addClass('focused');
        }
        $(tooltip_zipcode).addClass('hidden');
    });

    inputDetailAddress.addEventListener('focus', () => {
        $(detailPlaceholder).addClass('focused');
        $(tooltip_detail).removeClass('hidden');
    });
    inputDetailAddress.addEventListener('blur', () => {
        if(inputDetailAddress.value==null || inputDetailAddress.value=='') {
            $(detailPlaceholder).removeClass('focused');
        } else {
            $(detailPlaceholder).addClass('focused');
        }
        $(tooltip_detail).addClass('hidden');
    });


    inputAddress.addEventListener('focus', () => {
        $(address_placeholder).addClass('focused');
        $(address_placeholder).removeClass('hidden');
    });
    inputAddress.addEventListener('blur', () => {
        if(inputAddress.value==null || inputAddress.value=='') {
            $(address_placeholder).removeClass('focused');
        } else {
            $(address_placeholder).addClass('focused');
        }
        $(tooltip_Address).addClass('hidden');
    });
    
    inputPW.addEventListener('focus', () => {
        $(pwPlaceholder).addClass('focused');
        $(tooltip_pw).removeClass('hidden');
    });
    inputPW.addEventListener('blur', () => {
        if(inputPW.value==null || inputPW.value=='') {
            $(pwPlaceholder).removeClass('focused');
        } else {
            $(pwPlaceholder).addClass('focused');
        }
        $(tooltip_pw).addClass('hidden');
    });

    inputPW_C.addEventListener('focus', () => {
        $(pwCPlaceholder).addClass('focused');
        $(tooltip_pwC).removeClass('hidden');
    });
    inputPW_C.addEventListener('blur', () => {
        if(inputPW_C.value==null || inputPW_C.value=='') {
            $(pwCPlaceholder).removeClass('focused');
        } else {
            $(pwCPlaceholder).addClass('focused');
        }
        $(tooltip_pwC).addClass('hidden');
    });

    inputEmail.addEventListener('focus', () => {
        console.log("실행됨");
        $(emailPlaceholder).addClass('focused');
        $(tooltip_email).removeClass('hidden');
    });
    inputEmail.addEventListener('blur', () => {
        console.log("실행됨");
        if(inputEmail.value==null || inputEmail.value=='') {
            $(emailPlaceholder).removeClass('focused');
        } else {
            $(emailPlaceholder).addClass('focused');
        }
        $(tooltip_email).addClass('hidden');
    });



    //패스워드 일치 확인
    var password = document.getElementById("password")
        ,confirmPassword = document.getElementById("pw_confirm");

    function validatePassword(){
        if(password.value != confirmPassword.value) { // 만일 두 인풋 필드값이 같지 않을 경우
            // setCustomValidity의 값을 지정해 무조건 경고 표시가 나게 하고
            confirmPassword.setCustomValidity("Passwords Don't Match");
        }
        else { // 만일 두 인풋 필드값이 같을 경우
            // 오류가 없으면 메시지를 빈 문자열로 설정해야한다. 오류 메시지가 비어 있지 않은 한 양식은 유효성 검사를 통과하지 않고 제출되지 않는다.
            // 따라서 빈값을 주어 submit 처리되게 한다
            confirmPassword.setCustomValidity('');
        }
    }

    password.onchange = validatePassword;
    confirmPassword.onkeyup = validatePassword;


});

