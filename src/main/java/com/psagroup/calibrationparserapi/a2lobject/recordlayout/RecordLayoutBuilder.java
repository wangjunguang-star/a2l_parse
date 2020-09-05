package com.psagroup.calibrationparserapi.a2lobject.recordlayout;

import com.psagroup.calibrationparserapi.a2lobject.Builder;


public class RecordLayoutBuilder implements Builder {
	RecordLayout record = new RecordLayout();

	public RecordLayout build() {
		RecordLayout recordToReturn = record;
		this.record = new RecordLayout();
		return recordToReturn;
	}

	public void label(String label) {
		this.record.setLabel(label);
	}

	public void fncValue(String fncvalue) {
		if (fncvalue.split("\\s+").length > 2) {
			if ("UBYTE".equals(fncvalue.split("\\s+")[2])) {
				this.record.setFncvalue(DataType.UBYTE);
			} else if ("SBYTE".equals(fncvalue.split("\\s+")[2])) {
				this.record.setFncvalue(DataType.SBYTE);
			} else if ("UWORD".equals(fncvalue.split("\\s+")[2])) {
				this.record.setFncvalue(DataType.UWORD);
			} else if ("SWORD".equals(fncvalue.split("\\s+")[2])) {
				this.record.setFncvalue(DataType.SWORD);
			} else if ("ULONG".equals(fncvalue.split("\\s+")[2])) {
				this.record.setFncvalue(DataType.ULONG);
			} else if ("SLONG".equals(fncvalue.split("\\s+")[2])) {
				this.record.setFncvalue(DataType.SLONG);
			} else if ("FLOAT32_IEEE".equals(fncvalue.split("\\s+")[2])) {
				this.record.setFncvalue(DataType.FLOAT32_IEEE);
			}
				
				if(fncvalue.split("\\s+")[3].equals("ROW_DIR")) {
					this.record.setRowdir(true);
				}else {
					this.record.setRowdir(false);
				}
			}
		
	}
	
	public void axisPtsX(String axisptsx) {
		if (axisptsx.split("\\s+").length > 2) {
			if ("UBYTE".equals(axisptsx.split("\\s+")[2])) {
				this.record.setAxisptsx(DataType.UBYTE);
			} else if ("SBYTE".equals(axisptsx.split("\\s+")[2])) {
				this.record.setAxisptsx(DataType.SBYTE);
			} else if ("UWORD".equals(axisptsx.split("\\s+")[2])) {
				this.record.setAxisptsx(DataType.UWORD);
			} else if ("SWORD".equals(axisptsx.split("\\s+")[2])) {
				this.record.setAxisptsx(DataType.SWORD);
			} else if ("ULONG".equals(axisptsx.split("\\s+")[2])) {
				this.record.setAxisptsx(DataType.ULONG);
			} else if ("SLONG".equals(axisptsx.split("\\s+")[2])) {
				this.record.setAxisptsx(DataType.SLONG);
			} else if ("FLOAT32_IEEE".equals(axisptsx.split("\\s+")[2])) {
				this.record.setAxisptsx(DataType.FLOAT32_IEEE);
			}
		
		}
	}
	
	
	public void axisPtsY(String axisptsy) {
		if (axisptsy.split("\\s+").length > 2) {
			if ("UBYTE".equals(axisptsy.split("\\s+")[2])) {
				this.record.setAxisptsy(DataType.UBYTE);
			} else if ("SBYTE".equals(axisptsy.split("\\s+")[2])) {
				this.record.setAxisptsy(DataType.SBYTE);
			} else if ("UWORD".equals(axisptsy.split("\\s+")[2])) {
				this.record.setAxisptsy(DataType.UWORD);
			} else if ("SWORD".equals(axisptsy.split("\\s+")[2])) {
				this.record.setAxisptsy(DataType.SWORD);
			} else if ("ULONG".equals(axisptsy.split("\\s+")[2])) {
				this.record.setAxisptsy(DataType.ULONG);
			} else if ("SLONG".equals(axisptsy.split("\\s+")[2])) {
				this.record.setAxisptsy(DataType.SLONG);
			} else if ("FLOAT32_IEEE".equals(axisptsy.split("\\s+")[2])) {
				this.record.setAxisptsy(DataType.FLOAT32_IEEE);
			}
			}
		
	}
	
	public void noAxisPtsX(String noaxisptsx) {
		if (noaxisptsx.split("\\s+").length > 2) {
			if ("UBYTE".equals(noaxisptsx.split("\\s+")[2])) {
				this.record.setNoaxisptsx(DataType.UBYTE);
			} else if ("SBYTE".equals(noaxisptsx.split("\\s+")[2])) {
				this.record.setNoaxisptsx(DataType.SBYTE);
			} else if ("UWORD".equals(noaxisptsx.split("\\s+")[2])) {
				this.record.setNoaxisptsx(DataType.UWORD);
			} else if ("SWORD".equals(noaxisptsx.split("\\s+")[2])) {
				this.record.setNoaxisptsx(DataType.SWORD);
			} else if ("ULONG".equals(noaxisptsx.split("\\s+")[2])) {
				this.record.setNoaxisptsx(DataType.ULONG);
			} else if ("SLONG".equals(noaxisptsx.split("\\s+")[2])) {
				this.record.setNoaxisptsx(DataType.SLONG);
			} else if ("FLOAT32_IEEE".equals(noaxisptsx.split("\\s+")[2])) {
				this.record.setNoaxisptsx(DataType.FLOAT32_IEEE);
			}
		
		}
	}
	
	public void noAxisPtsY(String noaxisptsy) {
		if (noaxisptsy.split("\\s+").length > 2) {
			if ("UBYTE".equals(noaxisptsy.split("\\s+")[2])) {
				this.record.setNoaxisptsy(DataType.UBYTE);
			} else if ("SBYTE".equals(noaxisptsy.split("\\s+")[2])) {
				this.record.setNoaxisptsy(DataType.SBYTE);
			} else if ("UWORD".equals(noaxisptsy.split("\\s+")[2])) {
				this.record.setNoaxisptsy(DataType.UWORD);
			} else if ("SWORD".equals(noaxisptsy.split("\\s+")[2])) {
				this.record.setNoaxisptsy(DataType.SWORD);
			} else if ("ULONG".equals(noaxisptsy.split("\\s+")[2])) {
				this.record.setNoaxisptsy(DataType.ULONG);
			} else if ("SLONG".equals(noaxisptsy.split("\\s+")[2])) {
				this.record.setNoaxisptsy(DataType.SLONG);
			} else if ("FLOAT32_IEEE".equals(noaxisptsy.split("\\s+")[2])) {
				this.record.setNoaxisptsy(DataType.FLOAT32_IEEE);
			}
			}
		
	}

	/**
	 * @return the record
	 */
	public RecordLayout getRecord() {
		return record;
	}

}
