/* Class AVLNode */
class AVLNode
{
    AVLNode left, right;
    Comparable data;
    int height;

    /* Constructor */
    public AVLNode()
    {
        left = null;
        right = null;
        data = null;
        height = 0;
    }
    /* Constructor */
    public AVLNode(Comparable n)
    {
        left = null;
        right = null;
        data = n;
        height = 0;
    }
}
 