package com.yaacoubi.klinkhammer;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.*;

/**
 * @author m.yaacoubi
 */
public class FileTreePanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * File system view.
	 */
	protected static FileSystemView fsv = FileSystemView.getFileSystemView();

	/**
	 * Renderer for the file tree.
	 * 
	 * @author m.yaacoubi
	 */
	private static class FileTreeCellRenderer extends DefaultTreeCellRenderer {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * Icon cache to speed the rendering.
		 */
		private java.util.Map<String, Icon> iconCache = new HashMap<String, Icon>();

		/**
		 * Root name cache to speed the rendering.
		 */
		private java.util.Map<File, String> rootNameCache = new HashMap<File, String>();

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.tree.DefaultTreeCellRenderer#getTreeCellRendererComponent(javax.swing.JTree,
		 *      java.lang.Object, boolean, boolean, boolean, int, boolean)
		 */
		@Override
		public Component getTreeCellRendererComponent(JTree tree, Object value,
				boolean sel, boolean expanded, boolean leaf, int row,
				boolean hasFocus) {
			FileTreeNode ftn = (FileTreeNode) value;
			File file = ftn.file;
			String filename = "";
			if (file != null) {
				if (ftn.isFileSystemRoot) {
					// long start = System.currentTimeMillis();
					filename = this.rootNameCache.get(file);
					if (filename == null) {
						filename = fsv.getSystemDisplayName(file);
						this.rootNameCache.put(file, filename);
					}
					// long end = System.currentTimeMillis();
					// System.out.println(filename + ":" + (end - start));
				} else {
					filename = file.getName();
				}
			}
			JLabel result = (JLabel) super.getTreeCellRendererComponent(tree,
					filename, sel, expanded, leaf, row, hasFocus);
			if (file != null) {
				Icon icon = this.iconCache.get(filename);
				if (icon == null) {
					// System.out.println("Getting icon of " + filename);
					icon = fsv.getSystemIcon(file);
					this.iconCache.put(filename, icon);
				}
				result.setIcon(icon);
			}
			return result;
		}
	}

	/**
	 * A node in the file tree.
	 * 
	 * @author m.yaacoubi
	 */
	private static class FileTreeNode implements TreeNode {
		/**
		 * Node file.
		 */
		private File file;

		/**
		 * Children of the node file.
		 */
		private File[] children;

		/**
		 * Parent node.
		 */
		private TreeNode parent;

		/**
		 * Indication whether this node corresponds to a file system root.
		 */
		private boolean isFileSystemRoot;

		/**
		 * Creates a new file tree node.
		 * 
		 * @param file
		 *            Node file
		 * @param isFileSystemRoot
		 *            Indicates whether the file is a file system root.
		 * @param parent
		 *            Parent node.
		 */
		public FileTreeNode(File file, boolean isFileSystemRoot, TreeNode parent) {
			this.file = file;
			this.isFileSystemRoot = isFileSystemRoot;
			this.parent = parent;
			this.children = this.file.listFiles();
			if (this.children == null)
				this.children = new File[0];
		}

		/**
		 * Creates a new file tree node.
		 * 
		 * @param children
		 *            Children files.
		 */
		public FileTreeNode(File[] children) {
			this.file = null;
			this.parent = null;
			this.children = children;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.tree.TreeNode#children()
		 */
		public Enumeration<?> children() {
			final int elementCount = this.children.length;
			return new Enumeration<File>() {
				int count = 0;

				/*
				 * (non-Javadoc)
				 * 
				 * @see java.util.Enumeration#hasMoreElements()
				 */
				public boolean hasMoreElements() {
					return this.count < elementCount;
				}

				/*
				 * (non-Javadoc)
				 * 
				 * @see java.util.Enumeration#nextElement()
				 */
				public File nextElement() {
					if (this.count < elementCount) {
						return FileTreeNode.this.children[this.count++];
					}
					throw new NoSuchElementException("Vector Enumeration");
				}
			};

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.tree.TreeNode#getAllowsChildren()
		 */
		public boolean getAllowsChildren() {
			return true;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.tree.TreeNode#getChildAt(int)
		 */
		public TreeNode getChildAt(int childIndex) {
			return new FileTreeNode(this.children[childIndex],
					this.parent == null, this);
		}

		@SuppressWarnings("unused")
		public File getChildFileAt(int childIndex) {
			return this.children[childIndex];
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.tree.TreeNode#getChildCount()
		 */
		public int getChildCount() {
			return this.children.length;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.tree.TreeNode#getIndex(javax.swing.tree.TreeNode)
		 */
		public int getIndex(TreeNode node) {
			FileTreeNode ftn = (FileTreeNode) node;
			for (int i = 0; i < this.children.length; i++) {
				if (ftn.file.equals(this.children[i]))
					return i;
			}
			return -1;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.tree.TreeNode#getParent()
		 */
		public TreeNode getParent() {
			return this.parent;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.tree.TreeNode#isLeaf()
		 */
		public boolean isLeaf() {
			return (this.getChildCount() == 0);
		}
	}

	public int getSelectionCount()
	{
		return tree.getSelectionCount();
	}

	public int[] getSelectionRows()
	{
		return tree.getSelectionRows();
	}

	public File[] getSelectedFiles()
	{
		List<File> list = new Vector<File>();
		int[] selectedRows = tree.getSelectionRows();
		FileTreeNode ftp = null;
		for(int selectedRow : selectedRows)
		{
			ftp = (FileTreeNode)tree.getPathForRow(selectedRow).getLastPathComponent();
			list.add(ftp.file);
		}
		Object[] objArr = list.toArray();
		return Arrays.copyOf(objArr, objArr.length, File[].class);
	}

	public String[] getSelectedPaths()
	{
		List<String> list = new ArrayList<String>();
		int[] selectedRows = tree.getSelectionRows();
		FileTreeNode ftp = null;
		for(int selectedRow : selectedRows)
		{
			ftp = (FileTreeNode)tree.getPathForRow(selectedRow).getLastPathComponent();
			list.add(ftp.file.getPath());
		}
		Object[] objArr = list.toArray();
		return Arrays.copyOf(objArr, objArr.length, String[].class);
	}

	public File[] getValidSelectedImagesPaths()
	{
		return getValidSelectedImagesPaths(getSelectedFiles());
	}

	private boolean continueRecursion = true;

	public File[] getValidSelectedImagesPaths(File[] list)
	{
		continueRecursion = true;
		List<File> retList = new ArrayList<File>();
		String fileName = null;
		String filePath = null;
		String[] fileParts = null;
		if(list != null)
		{
			for(File file : list)
			{
				if(!continueRecursion) break; 
				fileName = file.getName();
				filePath = file.getPath();
				fileParts = filePath.split("\\.");
				if(file.isFile() && fileParts.length > 1 && isValidImage(fileParts[fileParts.length-1]))
					retList.add(file);
				else if(file.isDirectory())
				{
					int answer = JOptionPane.showConfirmDialog(null, "Do you want to add all valid pictures in the folder " + fileName + " to the list?", "Action for folder " + fileName + " required", JOptionPane.YES_NO_CANCEL_OPTION);
					if(answer == JOptionPane.YES_OPTION)
						retList.addAll(Arrays.asList(getValidSelectedImagesPaths(file.listFiles())));
					else if(answer == JOptionPane.CANCEL_OPTION)
						continueRecursion = false;
				}
			}
		}
		Object[] objArr = retList.toArray();
		return Arrays.copyOf(objArr, objArr.length, File[].class);
	}

	private boolean isValidImage(String ext)
	{
		String[] validExt = {"jpg", "jpeg", "tif", "tiff"};

		ext = ext.trim().toLowerCase();
		for(String tmpExt : validExt) if(ext.equals(tmpExt.toLowerCase())) return true;
		return false;
	}

	public void addTreeSelectionListener(javax.swing.event.TreeSelectionListener tsl)
	{
		tree.addTreeSelectionListener(tsl);
	}

	/**
	 * The file tree.
	 */
	private JTree tree;

	/**
	 * Creates the file tree panel.
	 */
	public FileTreePanel() {
		setLayout(new BorderLayout());

		File[] roots = File.listRoots();
		FileTreeNode rootTreeNode = new FileTreeNode(roots);
		tree = new JTree(rootTreeNode);
		tree.setCellRenderer(new FileTreeCellRenderer());
		tree.setRootVisible(false);
		final JScrollPane jsp = new JScrollPane(this.tree);
		jsp.setBorder(new EmptyBorder(0, 0, 0, 0));
		add(jsp, BorderLayout.CENTER);
		tree.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_F2)	//	Rename function
				{
					if(tree.getSelectionCount() == 1)
						JOptionPane.showInputDialog(null, "Rename to:");
					else
						JOptionPane.showMessageDialog(null, "Cannot rename " + tree.getSelectionCount() + " files/folders.");
				}
			}
		});
	}
}