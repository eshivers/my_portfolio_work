'use strict';
const {
  Model
} = require('sequelize');
module.exports = (sequelize, DataTypes) => {
  class GroupLine extends Model {
    /**
     * Helper method for defining associations.
     * This method is not a part of Sequelize lifecycle.
     * The `models/index` file will call this method automatically.
     */
    static associate(models) {
      // define association here
      GroupLine.belongsTo(models.User,{foreignKey: 'user_id' });
      GroupLine.belongsTo(models.Group,{foreignKey: 'group_id' });
    }
  };
  GroupLine.init({
    user_id: {
      type: DataTypes.INTEGER,
      references: {
          model: 'Users',
          key: 'id'
      }
    },
    group_id:{
      type: DataTypes.INTEGER,
      references: {
          model: 'Groups',
          key: 'id'
      }
    },
    line_text: {
      type: DataTypes.STRING,
      allowNull: false,
    }
  }, {
    sequelize,
    modelName: 'GroupLine',
  });
  return GroupLine;
};