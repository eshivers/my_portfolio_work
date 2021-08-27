'use strict';
const {
  Model
} = require('sequelize');
module.exports = (sequelize, DataTypes) => {
  class Friends extends Model {
    /**
     * Helper method for defining associations.
     * This method is not a part of Sequelize lifecycle.
     * The `models/index` file will call this method automatically.
     */
    static associate(models) {
      // define association here
      Friends.belongsTo(models.User,{foreignKey: 'user_id' });
      Friends.belongsTo(models.User,{foreignKey: 'friend_id' });
    }
  };
  Friends.init({
    user_id: {
      type: DataTypes.INTEGER,
      references: {
          model: 'Users',
          key: 'id'
      },
      primaryKey: true
    },
    friend_id: {
      type: DataTypes.INTEGER,
      references: {
          model: 'Users',
          key: 'id'
      },
      primaryKey: true
    }
  }, {
    sequelize,
    modelName: 'Friends',
  });
  return Friends;
};