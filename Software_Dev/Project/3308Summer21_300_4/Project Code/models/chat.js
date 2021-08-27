'use strict';
const {
  Model
} = require('sequelize');
module.exports = (sequelize, DataTypes) => {
  class Chat extends Model {
    /**
     * Helper method for defining associations.
     * This method is not a part of Sequelize lifecycle.
     * The `models/index` file will call this method automatically.
     */
    static associate(models) {
      // define association here
      Chat.hasMany(models.ChatLine,{foreignKey: 'chat_id'})
      Chat.hasMany(models.SharedChat,{foreignKey: 'chat_id'})
      Chat.belongsTo(models.User,{foreignKey: 'created_by' });

    }
  };
  Chat.init({
    created_by: {
      type: DataTypes.INTEGER,
      references:{
        model: 'Users',
        key: 'id'
      }
    }
  }, {
    sequelize,
    modelName: 'Chat',
  });
  return Chat;
};
