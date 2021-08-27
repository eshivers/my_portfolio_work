'use strict';
const {
  Model
} = require('sequelize');
module.exports = (sequelize, DataTypes) => {
  class UserFav extends Model {
    /**
     * Helper method for defining associations.
     * This method is not a part of Sequelize lifecycle.
     * The `models/index` file will call this method automatically.
     */
    static associate(models) {
      // define association here
      UserFav.belongsTo(models.User);
      UserFav.belongsTo(models.Destination);
    }
  };
  UserFav.init({
    dest_id: {
      type: DataTypes.INTEGER,
      references: {
          model: 'Destinations',
          key: 'id'
      }
    },
    user_id:{
          type: DataTypes.INTEGER,
          references: {
              model: 'Users',
              key: 'id'
          }
    }
  }, {
    sequelize,
    modelName: 'UserFav',
  });
  return UserFav;
};