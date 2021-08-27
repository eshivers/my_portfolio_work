// Load the modules
var express = require('express'); //Express - a web application framework that provides useful utility functions like 'http'
var app = express();
var bodyParser = require('body-parser'); // Body-parser -- a library that provides functions for parsing incoming requests
app.use(bodyParser.json());              // Support json encoded bodies
app.use(bodyParser.urlencoded({ extended: true })); // Support encoded bodies
const axios = require('axios');
const qs = require('query-string');

// Set the view engine to ejs
app.set('view engine', 'ejs');
app.use(express.static(__dirname + '/'));// Set the relative path; makes accessing the resource directory easier


// Home page - DON'T CHANGE
app.get('/', function(req, res) {
  res.render('pages/NYTimes_home', {
    my_title: "NYTimes search",
    items: '',
    error: false,
    message: ''
  });
});

//to request data from API for given search criteria
//TODO: You need to edit the code for this route to search for movie reviews and return them to the front-end
app.post('/get_feed', function(req, res) {
  var title = req.body.title; //TODO: Remove null and fetch the param (e.g, req.body.param_name); Check the NYTimes_home.ejs file or console.log("request parameters: ", req) to determine the parameter names
  var api_key = 'WQAfMuAO6MAJKQM3T1uv9w5jG9IRWcg4'; // TOOD: Remove null and replace with your API key you received at the setup

  if(title) {
    axios({
      url: `https://api.nytimes.com/svc/movies/v2/reviews/search.json?query=${title}&api-key=${api_key}`,
        method: 'GET',
        dataType:'json',
      })
        .then(items => {
          // TODO: Return the reviews to the front-end (e.g., res.render(...);); Try printing 'items' to the console to see what the GET request to the Twitter API returned.
          // Did console.log(items) return anything useful? How about console.log(items.data.results)?
          // Stuck? Look at the '/' route above
          console.log(items.data.results);
          res.render('pages/NYTimes_home', {
            my_title: "NYTimes Movie Reviews",
            items: items.data.results,
            error: false,
            message: ''
          });
        })
        .catch(error => {
          console.log(error);
          res.render('pages/NYTimes_home',{
            my_title: "NYTimes Movie Reviews",
            items: '',
            error: true,
            message: error
          })
        });


  }
  else {
    // TODO: Render the home page and include an error message (e.g., res.render(...);); Why was there an error? When does this code get executed? Look at the if statement above
    // Stuck? On the web page, try submitting a search query without a search term
    res.render('pages/NYTimes_home',{
      my_title: "NYTimes Search",
      items: '',
      error: true,
      message: 'Search field can\'t be empty'
    })
  }
});


app.listen(3000);
console.log('3000 is the magic port');
