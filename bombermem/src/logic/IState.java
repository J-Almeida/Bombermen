package logic;

/**
 * Abstract class that defines an application state
 */
public interface IState
{
    /** Initialization of some variables */
    void Initialize();
    /** Load to memory different files (sounds, images and others). Called after Initialize() */
    void LoadContents();
    /** Called every frame, updates the "state" */
    void Update(int diff);
    /** Frees the memory of allocated resources in LoadContents() */
    void UnloadContents();
}
