Summary: symnote
Name: symnote
Version: 1.0.0
Release: 1
License: Unknown
Vendor: Unknown
%if "x" != "x"
URL:
%endif
%if "x/opt" != "x"
Prefix: /opt
%endif
Provides: symnote
%if "x" != "x"
Group:
%endif
Autoprov: 0
Autoreq: 0
%if "xalsa-lib, glibc, libX11, libXau, libXext, libXi, libXrender, libXtst, libxcb, xdg-utils" != "x" || "x" != "x"
Requires: alsa-lib, glibc, libX11, libXau, libXext, libXi, libXrender, libXtst, libxcb, xdg-utils
%endif
%define __jar_repack %{nil}
%define package_filelist %{_tmppath}/%{name}.files
%define app_filelist %{_tmppath}/%{name}.app.files
%define filesystem_filelist %{_tmppath}/%{name}.filesystem.files
%define default_filesystem / /opt /usr /usr/bin /usr/lib /usr/local /usr/local/bin /usr/local/lib
%description
symnote
%global __os_install_post %{nil}
%prep
%build
%install
rm -rf %{buildroot}
install -d -m 755 %{buildroot}/opt/symnote
cp -r %{_sourcedir}/opt/symnote/* %{buildroot}/opt/symnote
if [ "$(echo %{_sourcedir}/lib/systemd/system/*.service)" != '%{_sourcedir}/lib/systemd/system/*.service' ]; then
  install -d -m 755 %{buildroot}/lib/systemd/system
  cp %{_sourcedir}/lib/systemd/system/*.service %{buildroot}/lib/systemd/system
fi
%if "x" != "x"
  %define license_install_file %{_defaultlicensedir}/%{name}-%{version}/%{basename:}
  install -d -m 755 "%{buildroot}%{dirname:%{license_install_file}}"
  install -m 644 "" "%{buildroot}%{license_install_file}"
%endif
(cd %{buildroot} && find . -path ./lib/systemd -prune -o -type d -print) | sed -e 's/^\.//' -e '/^$/d' | sort > %{app_filelist}
{ rpm -ql filesystem || echo %{default_filesystem}; } | sort > %{filesystem_filelist}
comm -23 %{app_filelist} %{filesystem_filelist} > %{package_filelist}
sed -i -e 's/.*/%dir "&"/' %{package_filelist}
(cd %{buildroot} && find . -not -type d) | sed -e 's/^\.//' -e 's/.*/"&"/' >> %{package_filelist}
%if "x" != "x"
  sed -i -e 's|"%{license_install_file}"||' -e '/^$/d' %{package_filelist}
%endif
%files -f %{package_filelist}
%if "x" != "x"
  %license "%{license_install_file}"
%endif
%post
package_type=rpm
xdg-desktop-menu install /opt/symnote/lib/symnote-symnote.desktop
ln -sf /opt/symnote/bin/symnote /usr/local/bin/symnote
%pre
package_type=rpm
if [ "$1" = 2 ]; then
  true;
fi
%preun
package_type=rpm
xdg-desktop-menu uninstall /opt/symnote/lib/symnote-symnote.desktop
if [ -L /usr/local/bin/symnote ]; then
  rm -f /usr/local/bin/symnote
fi
%clean