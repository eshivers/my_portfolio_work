'use strict';
const {
  Model
} = require('sequelize');
module.exports = (sequelize, DataTypes) => {
  class ChatLine extends Model {
    /**
     * Helper method for defining associations.
     * This method is not a part of Sequelize lifecycle.
     * The `models/index` file will call this method automatically.
     */
    static associate(models) {
      // define association here
      ChatLine.belongsTo(models.User,{foreignKey: 'user_id' });
      ChatLine.belongsTo(models.Chat,{foreignKey: 'chat_id' });
    }
  };
  ChatLine.init({
    user_id: {
      type: DataTypes.INTEGER,
      references: {
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
    },
    line_text: {
          type: DataTypes.STRING,
          allowNull: false,
    }
  }, {
    sequelize,
    modelName: 'ChatLine',
  });
  return ChatLine;
};