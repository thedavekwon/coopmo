export function likeTransaction(domainName, transactionId, transactionType) {
    const requestEndpoint = ''.concat(domainName, "/transaction/likeTransaction");
    console.log(requestEndpoint);
    const request = new Request(requestEndpoint, {
            method: "POST",
            headers: {
                "Access-Control-Allow-Origin": "*",
                "Cache-Control": "no-cache",
                "Content-Type": "application/json"},
            credentials: 'include',
            body: JSON.stringify({
                transactionId: transactionId,
                transactionType: transactionType
            })
        }
    );
    console.log(request);
    return fetch(request);
}