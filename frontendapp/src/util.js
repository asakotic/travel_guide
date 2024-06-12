export function parseJwt(token) {
    var base64Url = token.split('.')[1];
    var base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    var jsonPayload = decodeURIComponent(window.atob(base64).split('').map(function (c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));

    return JSON.parse(jsonPayload);
}


export function jacksonDateToDate(jacksonDate) {
    const lmao = [...jacksonDate];
    while (lmao.length < 6) lmao.push(0);
    const [year, month, day, hour, minute, second] = lmao;
    return new Date(year, month - 1, day, hour, minute, second);
}

