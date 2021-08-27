const express = require('express');
const router = express.Router();
const bcrypt = require("bcrypt");
const passport = require('passport');
var User = require('../models').User;
var Friends = require('../models').Friends;
const db = require('../models');
const path = require('path')
const { body,check, validationResult } = require('express-validator');
const ensureAuthenticated = require("./auth")
const moment = require("moment")
const { Op } = require("sequelize")

var authUser;

// Register Form
router.get('/register', async (req, res) => {
    res.render('pages/Register', {
      loggedIn: false
    })
});

router.post('/register', [
  check('fullName','This username must me 3+ characters long').not().isEmpty().isLength({ min: 3 }),
  check('emailAddress','Please enter a validate email')
    .not().isEmpty().withMessage('Email cannot be empty')
    .isEmail().withMessage('Email is not valid')
    .normalizeEmail().custom(value => {
      return User.findOne({where:{email:value}}).then(user => {
        if (user) {
          return Promise.reject('E-mail already in use');
        }
      });
    }),
  check('PhoneNumber','Please enter a validate email')
    .not().isEmpty().withMessage('Phone number cannot be empty')
    .isMobilePhone().withMessage('Phone number not valid'),
  check('passwordFirst')
    .not().isEmpty().withMessage('Email cannot be empty')
    .isStrongPassword({ minLength: 8, minLowercase: 1, minUppercase: 1, minNumbers: 1, minSymbols: 0})
    .withMessage('Password not strong enough: Should be of length 8 with 1 Lowercase,1 Uppercase and 1 Symbol'),
  check('passwordConfirm').not().isEmpty().withMessage('Confirm Password cannot be empty').custom((value, { req }) => {
    if (value !== req.body.passwordFirst) {
      throw new Error('Password confirmation does not match password');
    }
    return true;
  }),
  check('dob','Please enter a valid Date of Birth').optional().isDate()
],
  async (req, res) => {
    const errors = validationResult(req)
    if(!errors.isEmpty()) {
        alert = errors.errors;
        console.log(alert);
        res.render('pages/Register', {
            alert,
            loggedIn: false
        })
    } else {
      const gender = null;
      const salt = await bcrypt.genSalt(10);
      const dob = moment(req.body.dob);
      console.log(req.body);
      const newUser = await User.create({
        name: req.body.fullName,
        email: req.body.emailAddress,
        password: await bcrypt.hash(req.body.passwordFirst, salt),
        phone_no: req.body.PhoneNumber,
        dob : dob.format(),
        user_rating: 0.0,
      });
      newUser.save();
      req.session.message = 'Registered Successfully, please login';
      res.redirect('/users/login');
  }
});


// Login Form
router.get('/login', async (req, res) => {
    res.render('pages/User_Login', {
      loginError: '',
      loggedIn: false
    });
});

// Login Process
router.post('/login', async (req, res, next) => {
  passport.authenticate('local', {
    successRedirect: '/',
    failureRedirect: '/users/login'
  })(req, res, next);
  // targetUser = await User.findOne({where:{email: req.body.username}});
  // if (targetUser != null) {
  //   if (req.body.password == targetUser.password) {
  //     authUser = req.body.username;
  //     console.log(req.body.username, " is logged in");
  //     res.redirect('/');
  //   }
  //   else {
  //     res.render('pages/User_Login', {
  //       loginError: 'Incorrect password!'
  //     })
  //   }
  // }
  // else {
  //   res.render('pages/User_Login', {
  //     loginError: 'Incorrect username!'
  //   })
  // }

});

// logout
router.get('/logout', ensureAuthenticated,async (req, res) => {
  req.logout();
  res.render('pages/User_Login',{
    loginError: 'You are logged out.',
    loggedIn: false
  });
});

router.get('/profile',async (req,res)=>{
  console.log(req.user)
  var authUser = await User.findOne({where:
  {
    id: req.user.id
  }})
  if(req.isAuthenticated()) {
    res.render('pages/MyAccount',{
      user: authUser,
      testVar: "hello!",
      loggedIn: true
    })
  }
});

router.post('/profile',[
  check('fullName','Full name must be 3+ characters long').not().isEmpty().isLength({ min: 3 }),
  check('emailAddress','Please enter a valid email')
    .not().isEmpty().withMessage('Email cannot be empty')
    .isEmail().withMessage('Email is not valid')
    .normalizeEmail().custom((value, { req })  => {
      return User.findOne({where:{
        email:value,
        id: {
          [Op.ne] : req.user.id
        }
      }}).then(user => {
        if (user) {
          return Promise.reject('E-mail already in use');
        }
      });
    }),
  check('PhoneNumber','Please enter a valid phone')
    .not().isEmpty().withMessage('Phone number cannot be empty')
    .isMobilePhone().withMessage('Phone number not valid'),
  check('passwordFirst')
    .optional(),
  check('passwordConfirm').optional().custom((value, { req }) => {
    if (value !== req.body.passwordFirst) {
      throw new Error('Password confirmation does not match password');
    }
    return true;
  }),
  check('dob','Please enter a valid Date of Birth').optional().isDate()
],async (req,res)=>{
  
  const errors = validationResult(req)
    if(!errors.isEmpty()) {
        alert = errors.errors;
        console.log(alert)
        res.render('pages/MyAccount', {
            alert,
            loggedIn: true,
            user: req.user
        })
    } else {
      const salt = await bcrypt.genSalt(10);
      const dob = moment(req.body.dob);
      const update_user = await User.update({
        name: req.body.fullName,
        email: req.body.emailAddress,
        phone_no: req.body.PhoneNumber,
        dob : dob ? dob.format() : new Date('10 Jul 1999'), //replace with req.user.dob
      },{
        where: {
          id: req.user.id
        }
      });
      if(req.body.passwordFirst != '' && req.body.passwordConfirm != ''){
        const update_user = await User.update({
          password: await bcrypt.hash(req.body.passwordFirst, salt),
        },{
          where: {
            id: req.user.id
          }
          }
        );
      }
      console.log('profile updated');
      req.session.message = 'Edited Successfully';
      res.redirect('/users/profile',);
    }
});

// Set appropriate redirects
router.get('/friends',async(req,res) => {
  const curr_user_id = 1; //replace with req.user.id
  const friends = await Friends.findAll({
    where: {
      [Op.or] : {
        user_id: curr_user_id,
        friend_id: curr_user_id
      }
    },
    include: [{model: User}]
  });

  console.log(friends);
  res.redirect('/');
});

router.get('/friends/:id/add',async(req,res) => {
  const curr_user_id = 1; //replace with req.user.id
  const new_friend = await Friends.create({user_id: curr_user_id,friend:req.params.id})
  new_friend.save();
  console.log(new_friend);
  // res.redirect('/friends')
  res.redirect('/');
});

router.get('/friends/:id',async(req,res) => {
  const curr_user_id = 1; //replace with req.user.id
  const friend = await db.sequelize.query("SELECT * FROM `Friends` WHERE (friend_id = 3 and user_id = 1) OR (friend_id = 1 and user_id = 3)");
  if(!friend){
    res.redirect('/friends')
  }
  console.log(friend[0][0]);
  let friend_info = null;
  if(friend[0][0].friend_id != curr_user_id){
    friend_info = await User.findByPk(friend[0][0].friend_id)
  } else {
    friend_info = await User.findByPk(friend[0][0].user_id)
  }
  console.log(friend_info.dataValues)
  res.redirect('/');
});

module.exports = router;
