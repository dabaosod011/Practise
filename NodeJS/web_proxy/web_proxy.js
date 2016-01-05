var http = require('http');
var net = require('net');
var url = require('url');

function request(cReq, cRes) {
    var client_ip = cReq.headers['x-forwarded-for'] || 
     		    cReq.connection.remoteAddress   || 
                    cReq.socket.remoteAddress       ||
                    cReq.connection.socket.remoteAddress;
    console.log(client_ip + ' requesting ' + cReq.url);
    var u = url.parse(cReq.url);

    var options = {
        hostname : u.hostname, 
        port     : u.port || 80,
        path     : u.path,       
        method     : cReq.method,
        headers     : cReq.headers
    };

    var pReq = http.request(options, function(pRes) {
        cRes.writeHead(pRes.statusCode, pRes.headers);
        pRes.pipe(cRes);
    }).on('error', function(e) {
        cRes.end();
    });

    cReq.pipe(pReq);
}

function connect(cReq, cSock) {
    var client_ip = cReq.headers['x-forwarded-for'] ||
                    cReq.connection.remoteAddress   ||
                    cReq.socket.remoteAddress       ||
                    cReq.connection.socket.remoteAddress;
    console.log(client_ip + " connecting " + cReq.url )
    var u = url.parse('http://' + cReq.url);

    var pSock = net.connect(u.port, u.hostname, function() {
        cSock.write('HTTP/1.1 200 Connection Established\r\n\r\n');
        pSock.pipe(cSock);
    }).on('error', function(e) {
        cSock.end();
    });

    cSock.pipe(pSock);
}

http.createServer()
    .on('request', request)
    .on('connect', connect)
    .listen(8888, '0.0.0.0');
