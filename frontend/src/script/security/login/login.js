
async function login () { 
    const email = document.getElementById("login").value;
    const password = document.getElementById("pass").value;

    const response = await fetch ("" ,  {
        method : "Post",
        headers : {"Content-Type" : "application/json"},
        body : JSON.stringify({email,password})
   });

}