#!/opt/homebrew/bin/node
const content = `
<html>
  <head><title>Hi</title></head>
  <body>
    <p>
    ${process.env.stdin}
    </p>
    <ul>
    ${Object.keys(process.env).map(key => `<li>${key}: ${process.env[key]}</li>`).join("\n")}
    </ul>
  </body>
</html>
`

process.stdout.write("Content-Type: text/html\r\n");
process.stdout.write(`Content-Length: ${content.length}`);
process.stdout.write("\r\n");
process.stdout.write(content);
