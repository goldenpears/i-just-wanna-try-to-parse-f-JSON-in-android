console.log('suppose to work');

function loadStart() {
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
      document.getElementById("dynamic_content").innerHTML =
      this.responseText;
    }
  };
  xhttp.open("GET", "html/start.html", true);
  xhttp.send();
}

function loadExamples() {
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
      document.getElementById("dynamic_content").innerHTML =
      this.responseText;
    }
  };
  xhttp.open("GET", "html/examples.html", true);
  xhttp.send();
}

function loadHelp() {
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
      document.getElementById("dynamic_content").innerHTML =
      this.responseText;
    }
  };
  xhttp.open("GET", "html/help.html", true);
  xhttp.send();
}
