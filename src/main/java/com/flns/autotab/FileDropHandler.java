package com.flns.autotab;
import javax.swing.TransferHandler;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileDropHandler extends TransferHandler {
    /**
     * Serial ID
     */
    private static final long serialVersionUID = 1L;
    @Override
    public boolean canImport(TransferHandler.TransferSupport support) {
        for (DataFlavor flavor : support.getDataFlavors()) {
            if (flavor.isFlavorJavaFileListType()) {
                return true;
            }
        }
        return false;
    }
    @Override
    @SuppressWarnings("unchecked")
    public boolean importData(TransferHandler.TransferSupport supp) {
      try {
        List<File> files = (List<File>) supp.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
        for(File f : files) {
          NewTABGUI.jta.setText(f.getAbsolutePath());
          break;
        } 
      } catch (UnsupportedFlavorException | IOException e) {
        return false;
      }
      return true;
    }
}
