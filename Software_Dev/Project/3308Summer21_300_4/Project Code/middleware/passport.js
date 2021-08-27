const LocalStrategy = require('passport-local').Strategy
var User = require('../models').User;
const bcrypt = require("bcrypt")
const { Op } = require("sequelize")

module.exports = function(passport){
    passport.use(new LocalStrategy(function(username,password,done){
        console.log("Running passport auth function!");
        User.findOne({
            where: {
                email: {
                    [Op.eq]: username
                }
            }
        }).then(user => {
            if (!user) {
                console.log('No user found!');
                return done(null, false, { message: 'No user found' });
            }
            // Match Password
            console.log('User found!');
            bcrypt.compare(password,user.dataValues.password).then((result)=>{
                if (result) {
                    console.log('Correct pw!');
                    return done(null, user);
                } else {
                    console.log('Wrong pw!');
                    return done(null, false, { message: 'Wrong password' });
                }
            }).catch((err)=>console.error(err))
            // if(password==user.dataValues.password) {
            //     console.log("plaintext password match!")
            //     return done(null, user);
            // }
            // else {
            //     console.log("plaintext password does not match!")
            //     return done(null, false, { message: 'Wrong password' });
            // }
            
        }).catch(err => {
            if(err) throw err;
        })
    }));

    passport.serializeUser(function(user,done){
        done(null,user.id)
    })

    passport.deserializeUser(function(id, done) {
        User.findByPk(id).then(function(user) {
            if (user) {
                done(null, user.get());
            } else {
                done(user.errors, null);
            } 
        });
    });
}