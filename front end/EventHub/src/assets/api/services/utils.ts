export const unsetStates=()=>{
    if (localStorage.getItem("token"))localStorage.removeItem("token");
    if (localStorage.getItem("role"))localStorage.removeItem("role"); 
    if (localStorage.getItem("ROLE_ADMIN"))localStorage.removeItem("ROLE_ADMIN");
    if (localStorage.getItem("ROLE_PROMOTER"))localStorage.removeItem("ROLE_PROMOTER");
    if (localStorage.getItem("ROLE_ATTRACTION"))localStorage.removeItem("ROLE_ATTRACTION");
    if (localStorage.getItem("ROLE_USER"))localStorage.removeItem("ROLE_USER");
    if (localStorage.getItem("login"))localStorage.removeItem("login");
    if (localStorage.getItem("attraction"))localStorage.removeItem("attraction"); 
}

export const formatDate=(date:string)=>{
    let dateObject = new Date(date.toString());

    return dateObject.toLocaleDateString("pt-br")
}

export const formatDateHour=(date:string)=>{
    let dateObject = new Date(date.toString());
    let dateString = dateObject.toLocaleDateString("pt-br");
    dateString += ` ${dateObject.getHours()}h${dateObject.getMinutes()}`
    return dateString;
}