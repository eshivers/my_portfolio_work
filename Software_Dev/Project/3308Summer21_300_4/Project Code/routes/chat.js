const express = require('express');
const router = express.Router();
var Chat = require('../models').Chat;
var ChatLine = require('../models').ChatLine;
var User = require('../models').User;
var SharedChat = require('../models').SharedChat;
const { Op } = require("sequelize")
const { check, validationResult } = require('express-validator');
const ensureAuthenticated = require("./auth")

router.get('/', async (req,res) => {
    const curr_user_id = req.user.id;
    var chats = await SharedChat.findAll({
      where:{
        user_id: curr_user_id
      },
      logging: false
    });
    var all_chat_ids = [];
    for(let i = 0; i<chats.length; i++){
      all_chat_ids.push(chats[i].chat_id);
    }
    /*var all_recievers = []
    for(let i = 0; i<all_chat_ids.length; i++){
      var temp = await SharedChat.findAll({
        where:{
          chat_id: all_chat_ids[i],
          user_id:{[Op.ne]: curr_user_id}
        }
      });
      for(let j = 0; j<temp.length; j++)
      {
        all_recievers.push(temp[j].user_id);
      }
    }
    console.log(all_chat_ids);
    console.log(all_recievers);*/
    res.render('pages/chat',{
        chats: chats,
        chat_messages: '',
        chat_id: '',
        curr_user_id: curr_user_id,
        loggedIn: true
    });
})

//When the user clicks on a chat, this loads that selected chat into the window
router.post('/select_chat', async (req, res) =>{
  console.log('Chat select success');
  var curr_user_id = req.user.id;
  var selected_chat = req.body.submit;
  if(selected_chat != null){
    var query = await ChatLine.findAll({
      where: {
        chat_id: selected_chat
      },
      logging: false
    });
    var chats = await SharedChat.findAll({
      where:{
        user_id: curr_user_id,
      },
      logging: false
    });
  }
  else{
    var query = '';
    var chats = '';
  }
	res.render('pages/chat',{
    chats: chats,
		chat_messages: query,
    chat_id: selected_chat,
    curr_user_id: curr_user_id,
        loggedIn: true
	});
});

//This recieves a message when the user sends one, puts in the database, and refreshes the chat
router.post('/send_message', async (req, res) =>{
  console.log('Message submitted')
  const curr_user_id = req.user.id;
  var chat_id = req.body.chat_id_send;
  if(chat_id == 0){
    console.log("You must send message to a user");
    res.render('pages/chat',{
        chats: chats,
        chat_messages: '',
        chat_id: '',
        curr_user_id: curr_user_id,
        loggedIn: true
    });
  }
  else{
    var new_message = await ChatLine.create({user_id:curr_user_id, chat_id:chat_id, line_text:req.body.message});
    var query = await ChatLine.findAll({
      where: {
        chat_id: chat_id
      },
      logging: false
    });

    var chats = await SharedChat.findAll({
      where:{
        user_id: curr_user_id
      },
      logging: false
    });
    res.render('pages/chat',{
      chat_messages: query,
      chats: chats,
      chat_id: chat_id,
      curr_user_id: curr_user_id,
      loggedIn: true
    })
  }
});


module.exports = router;
