package com.pb.dashboard.tester.middleware;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.pb.dashboard.tester.middleware.holder.EASInfoHolder;
import com.pb.dashboard.tester.middleware.holder.ServerInfoHolder;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class DataReader {

    private static final Logger LOG = Logger.getLogger(DataReader.class);
    private static final String easPathToDBData = "EAServer/Repository/ConnCache/BiplaneCache.props"; // file to parse
    private static final String easPathToAppName = "EAServer/Repository/Application"; // find file name
    private static final String easPathToPortNumber = "EAServer/Repository/Listener/Jaguar_iiop.props"; // file to parse

    private static final String easBiplCacheStart = "com.sybase.jaguar.conncache.remotesvrname";
    private static final String easJagIiopStart = "com.sybase.jaguar.listener.port";

    private EASDataParser parser = new EASDataParser();

    public void loadServerInfoHolder(ServerInfoHolder serverInfoHolder) {
        JSch jsch = new JSch();
        try {
            /* Resource allocation */
            Session session = jsch.getSession(serverInfoHolder.getUsername(), serverInfoHolder.getServerIP());
            session.setPort(22);
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword(serverInfoHolder.getPassword());
            session.connect();
            Channel channel = session.openChannel("sftp");
            channel.connect();
            ChannelSftp sftpChannel = (ChannelSftp) channel;

            EASInfoHolder[] easInfoHolders = serverInfoHolder.getEasInfoHolders();
            for (EASInfoHolder eas : easInfoHolders) {
                /* Read data from remote server */
                                                 /* EAS */
                // DB data
                List<ChannelSftp.LsEntry> list = new ArrayList<ChannelSftp.LsEntry>();
                try {
                    InputStream inputStream = sftpChannel.get(eas.getBaseURL() + easPathToDBData);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                    for (String line; (line = reader.readLine()) != null; ) {
                        if (line.startsWith(easBiplCacheStart)) {
                            eas.setDbIP(parser.parseDbIP(line));
                            eas.setDbName(parser.parseDbName(line));
                            break;
                        }
                    }
                    reader.close();
                    inputStream.close();
                    // Port number
                    inputStream = sftpChannel.get(eas.getBaseURL() + easPathToPortNumber);
                    reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                    for (String line; (line = reader.readLine()) != null; ) {
                        if (line.startsWith(easJagIiopStart)) {
                            eas.setPort(parser.parsePort(line));
                            break;
                        }
                    }
                    reader.close();
                    inputStream.close();
                    // App names
                    list = sftpChannel.ls(eas.getBaseURL() + easPathToAppName);
                } catch (Exception e) {
                    LOG.error(e.getMessage());
                }
                List<String> apps = new ArrayList<String>();
                if (!list.isEmpty()) {

                    for (ChannelSftp.LsEntry f : list) {
                        String filename = f.getFilename();
                        if (!filename.equals(".") && !filename.equals("..")) apps.add(parser.parseAppName(filename));
                    }

                }
                eas.setApps(apps.toArray(new String[apps.size()]));
            }

                                                       /* GlassFish */


            /* Clean up */
            sftpChannel.exit();
            channel.disconnect();
            session.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
