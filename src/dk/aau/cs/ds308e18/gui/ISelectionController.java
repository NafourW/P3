package dk.aau.cs.ds308e18.gui;

/*
Interface used for gui views that focus on a specific object
(For example "Edit Tour" is focused on a specific selected tour)
*/
public interface ISelectionController {
    void setSelectedObject(Object obj, boolean isNew);
}
