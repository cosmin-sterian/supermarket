
public interface Visitor {
	//public void visit(Department department);
	public void visit(BookDepartment bookDepartment);
	public void visit(MusicDepartment musicDepartment);
	public void visit(SoftwareDepartment softwareDepartment);
	public void visit(VideoDepartment videoDepartment);
}
