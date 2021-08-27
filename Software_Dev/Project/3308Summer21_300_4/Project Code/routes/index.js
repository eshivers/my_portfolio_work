const express = require('express')
const router = express.Router()
const ensureAuthenticated = require("./auth")
const path = require('path')
const passport = require('passport')

router.get('/',async (req,res)=>{
    var loggedIn = req.isAuthenticated();
    res.render('pages/HomeMain', {
        loggedIn: loggedIn
    })
})

router.get('/map',async (req,res) => {
    var loggedIn = req.isAuthenticated();
    res.render('pages/map', {
        loggedIn: loggedIn
    })
})

// router.get('/map',ensureAuthenticated,async (req,res) => {
//     res.sendFile(path.join(__dirname+ "/../../Front-End/views/map.html"))
// })

router.get('/about',async (req,res)=>{
    var loggedIn = req.isAuthenticated();
    res.render('pages/about', {
        loggedIn: loggedIn
    })
})

module.exports = router