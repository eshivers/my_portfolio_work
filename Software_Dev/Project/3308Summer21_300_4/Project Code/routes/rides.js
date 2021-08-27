const express = require('express');
const router = express.Router();
var Ride = require('../models').Ride;
var RideUser = require('../models').RideUser;
var RideRate = require('../models').RideRate;
var Group = require('../models').Group;
var GroupLine = require('../models').GroupLine;
var User = require('../models').User;
var Destination = require('../models').Destination;
var Chat = require('../models').Chat;
var SharedChat = require('../models').SharedChat;
const { check, validationResult } = require('express-validator');
const ensureAuthenticated = require("./auth")
var path = require('path')
const { Op } = require("sequelize")
const moment = require("moment")

router.get('/search',async (req,res) => {
    let destinations = await Destination.findAll();
    destinations = destinations.map(element => element.dataValues);
    var loggedIn = req.isAuthenticated();
    res.render('pages/rideSearch',{
        rides: null,
        destinations: destinations,
        loggedIn: loggedIn
    });
});

router.post('/search',[
    // validate input
    check('start_date').not().isEmpty(),
    check('end_date').not().isEmpty(),
    check('origin'),
    check('destination'),
],
    async (req,res) => {
        const errors = validationResult(req);
        var loggedIn = req.isAuthenticated();
        if(!errors.isEmpty()) {
            const alert = errors.array()
            console.log(alert);
            res.render('pages/rideSearch', {
                alert,
                loggedIn: loggedIn
            })
        } else {
            // collect search criteria
            var start_date = moment(req.body.start_date);
            var end_date = moment(req.body.end_date);
            //var dest_id = parseInt(req.body.destination);
            // build query
            const where_clause = {
                departure: {
                    [Op.gte] : start_date.toDate(),
                    [Op.lt] : end_date.toDate(),
                },
                end_date: {
                    [Op.gt] : start_date.toDate(),
                    [Op.lte] : end_date.toDate(),
                },
            };
            var dest_include = {model: Destination};
            console.log(req.body.origin);
            console.log(req.body.destination);
            if(req.body.origin != ''){
                var searchTerm = req.body.origin;
                // Tokenize the search terms and remove empty spaces
                var tokens = searchTerm
                            .toLowerCase()
                            .split(' ')
                            .filter(function(token){
                                return token.trim() !== '';
                            });
                var searchTermRegex = null;
                if(tokens.length) {
                    searchTermRegex = tokens.join('|');
                }
                where_clause.start_point = {[Op.iRegexp] : searchTermRegex};
            }
            if(req.body.destination != ''){
                var searchTerm_dest = req.body.destination;
                // Tokenize the search terms and remove empty spaces
                var tokens = searchTerm_dest
                            .toLowerCase()
                            .split(' ')
                            .filter(function(token){
                                return token.trim() !== '';
                            });
                var searchTermRegex_dest = null;
                if(tokens.length) {
                    searchTermRegex_dest = tokens.join('|');
                }
                dest_include.where = {
                    name: {
                        [Op.iRegexp] : `${searchTermRegex_dest}`
                    }
                }
            }
            console.log(where_clause);
            let rides = await Ride.findAll({
                where: where_clause,
                include: [
                    {model: User},
                    dest_include
                ],
            });
            rides = rides.map(element => element.dataValues);
            console.log("results:",rides);
            let destinations = await Destination.findAll();
            //destinations = destinations.map(element => element.dataValues);
            res.render('pages/rideSearch',{
                rides: rides,
                destinations: destinations,
                loggedIn: loggedIn
            });
        }
    }
);

// Ride Dashboard
router.get('/',async (req,res) => {
    var loggedIn = req.isAuthenticated();
    const driven_rides = await Ride.findAll({
        where: {
            driver_id: 1, //replace with req.user.id
        }
    });
    driven_rides = driven_rides.map(element => element.dataValues);
    let rides = await RideUser.findAll({
        where: {
            user_id: 1 //replace with req.user.id
        },
        include: [
            {model:Ride},
        ]
    });
    rides = rides.map(element => element.dataValues);
    res.render('pages/rides',{
        driven_rides: driven_rides,
        rides: rides,
        loggedIn: loggedIn
    });
});

// Add Ride
router.get('/add',async (req,res) => {
    let destinations = await Destination.findAll();

    destinations = destinations.map(element => element.dataValues);
    res.render('pages/add_ride',{
        destinations: destinations,
        loggedIn: true
    });
});

router.post('/add',[
    // Validate ride data
    check('start_date').not().isEmpty(),
    check('end_date').not().isEmpty(), /*.custom((value, { req }) => {
        if (value <= moment(req.body.end_date).toDate()) {
          throw new Error('Return Date can\'t be before departure');
        }
        return true;
      }),*/
    check('origin').not().isEmpty(),
    check('destination').not().isEmpty(),
    check('seats').not().isEmpty().isInt(),
    check('car_make'),
    check('car_model'),
    check('fare_share'),
],
    async (req,res) => {
        const errors = validationResult(req)
        const curr_user_id = req.user.id;
        if(!errors.isEmpty()) {
            const alert = errors.array()
            console.log(alert);
            let destinations = await Destination.findAll();
            destinations = destinations.map(element => element.dataValues);
            res.render('pages/add_ride', {
                destinations: destinations,
                error: errors,
                loggedIn: true
            });
        } else {
            // Add entry to ride table
            console.log("the new ride starts",req.body.start_date);
            console.log("the new ride ends",req.body.end_date);
            const start_date = moment(req.body.start_date);
            const end_date = moment(req.body.end_date);
            const newRide = await Ride.create({
                departure: start_date.toDate(),
                start_point: req.body.origin,
                end_date: end_date.toDate(),
                fare_share: req.body.fare_share != '' ? req.body.fare_share : null,
                vehicle_make: req.body.car_make != '' ? req.body.car_make : null,
                vehicle_model: req.body.car_model != '' ? req.body.car_model : null,
                seats_available : parseInt(req.body.seats),
                driver_id: 1, //replace with req.user.id
                dest_id: parseInt(req.body.destination),

            });
            //Creates new chat for ride
            var new_chat = await Chat.create({created_by:curr_user_id});
            var new_group = await SharedChat.create({user_id:curr_user_id, chat_id:new_chat.id});

            console.log("Creating ride with following parameters: ",newRide);
            newRide.save();
            new_chat.save();
            new_group.save();
            req.session.message = 'Added ride successfully';
            res.redirect(`/rides/${newRide.id}`);
        }
    }
);

router.get('/:id',async (req,res) => {
    var loggedIn = req.isAuthenticated();
    const ride = await Ride.findOne({
        where: {
            id: {
                [Op.eq]:req.params.id

            }
        },
        include: [
            {model:Destination}
        ]
    });
    let destinations = await Destination.findAll();
    destinations = destinations.map(element => element.dataValues);
    console.log(ride)
    res.render('pages/ride',{
        ride: ride.dataValues,
        destinations: destinations,
        loggedIn: loggedIn
    });
});

// Edit Ride
router.post('/edit/:id',[
    check('depart_date').not().isEmpty().isAfter(),
    check('return_date').not().isEmpty().custom((value, { req }) => {
        if (moment(value).toDate() <= moment(req.body.depart_date).toDate()) {
          throw new Error('Return Date can\'t be before departure');
        }
        return true;
      }),
    check('origin').not().isEmpty(),
    check('destination').not().isEmpty(),
    check('seats').not().isEmpty().isInt(),
    check('car_make'),
    check('car_model'),
    check('fare_share'),
],
    async (req,res) => {
        const errors = validationResult(req)
        if(!errors.isEmpty()) {
            const alert = errors.array()
            res.render('/rides/add', {
                alert,
                loggedIn: true
            });
        } else {

            const start_date = moment(req.body.start_date).format();
            const end_date = moment(req.body.end_date).format();
            let update_object = {
                departure: start_date.toDate(),
                start_point: req.body.origin,
                end_date: end_date.toDate(),
                seats_available : parseInt(req.body.seats),
                dest_id: parseInt(req.body.destination),
            }
            if(req.body.fare_share != ''){
                update_object.push({fare_share: req.body.fare_share})
            }
            if(req.body.vehicle_model != ''){
                update_object.push({vehicle_model: req.body.vehicle_model})
            }
            if(req.body.vehicle_make != ''){
                update_object.push({vehicle_make: req.body.vehicle_make})
            }
            const newRide = await Ride.update(update_object,{
                where:{
                    id: req.params.id
                }
            });
            newRide.save();
            res.redirect(`/rides/${newRide.id}`,{
                message : 'Updated ride successfully'
            });
        }
    }
);

// Delete Ride
router.delete('/delete/:id',async (req,res) => {
    let cascade_delete = await RideUser.destroy({
        where: {
            ride_id: req.params.id
        }
    });
    let ride_rate_delete = await RideRate.destroy({
        where: {
            ride_id: req.params.id
        }
    });
    let rows_deleted = await Ride.destroy({
        where: {
            id: req.params.id
        }
    });
    res.redirect(`/rides/`,{
        message : 'Deleted ride successfully'
    });
});

// Add Rating Routes
router.get('/:id/rate/:user_id',async(req,res) => {
    if(req.query.rating){
        const rating = req.query.rating;
        const ride_rating = await RideRate.findOne({
            where:{
                ride_id: {
                    [Op.eq] : parseInt(req.params.id)
                },
                ratee_id: {
                    [Op.eq] : parseInt(req.params.user_id)
                },
                rater_id: {
                    [Op.eq] : parseInt(1) // replace with req.user.id
                }
            }
        });
        if(!ride_rating){
            const new_rating = await RideRate.create({
                rating: rating,
                rater_id: 1, //replace with req.user.id
                ratee_id: req.params.user_id,
                ride_id: req.params.id
            });
            new_rating.save();
        } else {
            const new_rating = await RideRate.update({
                rating: rating
            },{
                where: {
                    id: {
                        [Op.eq] : ride_rating.id
                    }
                }
            });
        }
        res.redirect({
            message : 'Updated ride successfully'
        },`/rides/${req.params.id}`);
    } else{
        res.redirect({
            alter : { user_id: req.params.user_id, message:'Please assign a rating'}
        },`/rides/${req.params.id}`);
    }
});

// Show group
router.get('/:id/group',async(req,res) => {
    let group = await Group.findOne({
        where: {
            ride_id : {
                [Op.eq] : req.params.id
            }
        },
    });
    console.log(group);
    let group_members = await GroupLine.findAll({
        where: {
            group_id: {
                [Op.eq]: group.id
            }
        },
        include: [{model: User}],
        group: 'user_id'
    });
    group_members = group_members.map(element => element.dataValues);
    console.log(group_members);
    let group_messages = await GroupLine.findAll({
        where: {
            group_id: {
                [Op.eq]: group.id
            }
        },
    });
    group_messages = group_messages.map(element => element.dataValues);
    console.log(group_messages);
    res.render('/pages/group',{
        group_members: group_members,
        group_messages: group_messages
    });
});

//Joing Chat
router.post('/join', async(req, res) => {
  var loggedIn = req.isAuthenticated();
  const curr_user_id = req.user.id;
  var chat_creator = req.body.joinButton;
  var chat_id = await Chat.findAll({
    where: {
      created_by: chat_creator
    }
  });
  var new_group = await SharedChat.create({user_id:curr_user_id, chat_id:chat_id[0].id});


  let destinations = await Destination.findAll();
  destinations = destinations.map(element => element.dataValues);
  var loggedIn = req.isAuthenticated();
  res.render('pages/rideSearch',{
      rides: null,
      destinations: destinations,
      loggedIn: loggedIn
  });
});

// Send Message

module.exports = router;
