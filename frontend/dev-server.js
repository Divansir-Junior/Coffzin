const fs = require("fs");
const http = require("http");
const path = require("path");

const root = path.resolve(__dirname, "..");
const port = Number(process.env.PORT || 5501);
const host = process.env.HOST || "127.0.0.1";

const contentTypes = {
    ".css": "text/css",
    ".html": "text/html",
    ".jpeg": "image/jpeg",
    ".jpg": "image/jpeg",
    ".js": "text/javascript",
    ".json": "application/json",
    ".png": "image/png"
};

http.createServer((request, response) => {
    const pathname = decodeURIComponent(request.url.split("?")[0]);
    const requestedPath = pathname === "/" ? "index.html" : pathname.replace(/^\/+/, "");
    const filePath = path.resolve(root, requestedPath);

    if (!filePath.startsWith(root)) {
        response.writeHead(403);
        response.end("Forbidden");
        return;
    }

    fs.stat(filePath, (error, stats) => {
        if (error || !stats.isFile()) {
            response.writeHead(404);
            response.end("Not found");
            return;
        }

        response.writeHead(200, {
            "Content-Type": contentTypes[path.extname(filePath).toLowerCase()] || "application/octet-stream"
        });
        fs.createReadStream(filePath).pipe(response);
    });
}).listen(port, host, () => {
    console.log(`Frontend running at http://${host}:${port}/`);
});
