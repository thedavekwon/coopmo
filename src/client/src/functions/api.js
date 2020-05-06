export function fetchFeed(userId, num_latest, fetch_type) {
    const requestEndpoint = ''.concat("http://localhost:8080/pay/getLatest/",
        fetch_type,
        "Payment",
        "?userId=",
        userId,
        "&n=",
        num_latest.toString()
    );
    const headers = new Headers({
        'Access-Control-Allow-Origin':'*'
    });
    console.log(requestEndpoint);
    const request = new Request(requestEndpoint, {
        method: "GET",
        headers: headers,
        mode: 'cors',
        cache: 'default',
        
        }
    );

    return fetch(request);
}

export function fetchBalance(userId) {
    const requestEndpoint = ''.concat("http://localhost:8080/user/getUserBalance/",
        "?userId=",
        userId,
    );
    const headers = new Headers({
        'Access-Control-Allow-Origin':'*'
    });
    const request = new Request(requestEndpoint, {
        method: "GET",
        headers: headers,
        mode: 'cors',
        cache: 'default',
        
        }
    );
    return fetch(request);
}

export function fetchFriendList(userId) {
    const requestEndpoint = ''.concat("http://localhost:8080/user/getUserFriendList/",
        "?userId=",
        userId,
    );
    const headers = new Headers({
        'Access-Control-Allow-Origin':'*'
    });
    const request = new Request(requestEndpoint, {
        method: "GET",
        headers: headers,
        mode: 'cors',
        cache: 'default',
        
        }
    );
    return fetch(request);
}