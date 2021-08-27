'use strict';
const {
  Model
} = require('sequelize');
module.exports = (sequelize, DataTypes) => {
  class SharedChat extends Model {
    /**
     * Helper method for defining associations.
     * This method is not a part of Sequelize lifecycle.
     * The `models/index` file will call this method automatically.
     */
    static associate(models) {
      // define association here
      SharedChat.belongsTo(models.User,{foreignKey: 'user_id' });
      SharedChat.belongsTo(models.Chat,{foreignKey: 'chat_id' });
    }
  };
  SharedChat.init({
    user_id: {
      type: DataTypes.INTEGER,
      references:{
        model: 'Users',
        key: 'id'
      }
    },
    chat_id:{
          type: DataTypes.INTEGER,
          references: {
              model: 'Chats',
              key: 'id'
          }
    }
  }, {
    sequelize,
    modelName: 'SharedChat',
  });
  return SharedChat;
};
