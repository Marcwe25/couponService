package DAODBlevel;

public class Statement {

	String tableName; // company
	String id; // id
	String itemName;// comp_name
	String[] otherColumns;// password,email
	String columnsNames;// (comp_name,password,email)
	String columnsParameter;// (?,?,?)

	public Statement(String...strings) {
		this.tableName = strings[0];
		this.id = strings[1];
		this.itemName = strings[2];
		otherColumns = new String[strings.length - 3];
		for (int i = 0; i < otherColumns.length; i++) {
			otherColumns[i] = strings[i + 3];
		}
		columnsNames = "(" + itemName;
		for (String string : otherColumns) {
			columnsNames += "," + string;
		}
		columnsNames += ")";
		columnsParameter = "(?";
		for (@SuppressWarnings("unused")
		String string : otherColumns) {
			columnsParameter += ",?";
		}
		columnsParameter += ")";

	}

	public String create() {
		String str = "INSERT INTO " + tableName + " " + columnsNames + " values " + columnsParameter;
		return str;
	}

	public String returnLastCreatedID() {
		String str = "SELECT IDENTITY_VAL_LOCAL() FROM " + tableName;
		return str;
	}

	public String delete() {
		String str = "DELETE FROM " + tableName;
		return str;
	}

	public String deleteByID() {
		String str = delete() + " WHERE "+this.id+"=?";
		return str;
	}

	public String selectAll() {
		String str = "SELECT * FROM " + tableName;
		return str;
	}

	public String selectByID() {
		String str = selectAll() + " where ID = ?";
		return str;
	}

	public String GetIDbyName() {
		String str = "SELECT "+id+" FROM " + tableName + " where " + this.itemName + " = ?";
		return str;
	}

	public String getPassword() {
		String str = "SELECT password FROM " + tableName + " where " + this.itemName + " = ?";
		return str;
	}

	public String update() {
		String str = "UPDATE " + tableName + " SET ";
		for (String string : otherColumns) {
			str += string + " = ? , ";
		}
		str = str.substring(0, str.length() - 2);
		str += " WHERE " + this.id + "=?";
		return str;
	}
}