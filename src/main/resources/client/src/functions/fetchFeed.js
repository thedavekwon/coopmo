export function fetchFeed(userId, num_latest, fetch_type) {
    const requestEndpoint = ''.concat(this.props.domainName + "/pay/getLatest",
        fetch_type,
        "Payment",
        "?userId=",
        userId,
        "&n=",
        num_latest.toString()
    );
    const headers = new Headers({
        'Access-Control-Allow-Origin': '*'
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