export function fetchProfilePic(domainName) {
    const requestEndpoint = domainName + "/user/getProfilePic";
    console.log(requestEndpoint);
    const request = new Request(requestEndpoint, {
            method: "GET",
            headers: {
                "Access-Control-Allow-Origin": "*",
                "Cache-Control": "no-cache"},
            credentials: 'include'
        }
    );

    return fetch(request);
}

export function fetchOthersProfilePic(domainName, userId) {
    const requestEndpoint = ''.concat(domainName + "/user/getOthersProfilePic?userId=", userId);
    console.log(requestEndpoint);
    const request = new Request(requestEndpoint, {
            method: "GET",
            headers: {
                "Access-Control-Allow-Origin": "*",
                "Cache-Control": "no-cache"},
            credentials: 'include'
        }
    );
    console.log(request);
    return fetch(request);
}